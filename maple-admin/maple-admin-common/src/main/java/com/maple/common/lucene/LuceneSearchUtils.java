package com.maple.common.lucene;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleBaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangfuzeng
 * @date 2024/1/9
 */
@Component
@Slf4j
public class LuceneSearchUtils {

    @Value("${file.lucenePath}")
    private String lucenePath;

    public IPage<LuceneDataModel> searchPage(LuceneDataPageQuery pageQuery) {
        String[] str = {"title", "description", "content"};
        Map<String, Float> boosts = new HashMap<>();
        boosts.put("title", 10F);
        boosts.put("description", 5F);
        boosts.put("content", 1F);
        
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<LuceneDataModel> pageReq = pageQuery.getPage();
        int current = (int) pageReq.getCurrent();
        int pageSize = (int) pageReq.getSize();
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(lucenePath));
             // 索引读取工具
             IndexReader reader = DirectoryReader.open(directory);) {
            // 索引搜索工具
            IndexSearcher searcher = new IndexSearcher(reader);
            // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
            MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new IKAnalyzer(true), boosts);
            // 创建查询对象
            Query query = parser.parse(pageQuery.getQuery().getTitle());
            // 分页获取数据
            TopDocs topDocs = searchByPage(current, pageSize, searcher, query);
            // 获取总条数
            log.info("本次搜索共找到" + topDocs.totalHits + "条数据");

            //高亮显示
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
            //高亮后的段落范围在100字内
            Fragmenter fragmenter = new SimpleFragmenter(100);
            highlighter.setTextFragmenter(fragmenter);

            // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<LuceneDataModel> list = new ArrayList<>();
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 取出文档编号
                int docId = scoreDoc.doc;
                // 根据编号去找文档
                Document doc = reader.document(docId);
                LuceneDataModel model = LuceneDataModel.builder()
                        .id(Long.valueOf(doc.get("id").replace("M", "")))
                        .title(doc.get("title"))
                        .description(doc.get("description"))
                        .type(Integer.valueOf(doc.get("type")))
                        .imageUrl(doc.get("imageUrl"))
                        .originalUrl(doc.get("originalUrl"))
                        .build();
                //处理高亮字段显示
                String title = highlighter.getBestFragment(new IKAnalyzer(), "title", doc.get("title"));
                String description = highlighter.getBestFragment(new IKAnalyzer(), "description", doc.get("description"));
                if(StringUtils.isNotBlank(title)) {
                    model.setTitle(title);
                }
                if(StringUtils.isNotBlank(description)) {
                    model.setDescription(description);
                }
                list.add(model);
            }
            IPage<LuceneDataModel> pages = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
            pages.setSize(pageSize);
            pages.setCurrent(current);
            pages.setRecords(list);
            pages.setTotal(topDocs.totalHits);
            pages.setPages(topDocs.totalHits / pageSize);
            return pages;

        } catch (Exception e) {
            log.error("查询数据异常", e);
            throw new MapleBaseException(ErrorCode.COMMON_ERROR.getCode(), "查询数据异常，请稍后再试");
        }
    }

    private TopDocs searchByPage(int page, int perPage, IndexSearcher searcher, Query query) throws IOException {
        TopDocs result;
        if (query == null) {
            log.info(" Query is null return null ");
            return null;
        }
        ScoreDoc before = null;
        if (page != 1) {
            TopDocs docsBefore = searcher.search(query, (page - 1) * perPage);
            ScoreDoc[] scoreDocs = docsBefore.scoreDocs;
            if (scoreDocs.length > 0) {
                before = scoreDocs[scoreDocs.length - 1];
            }
        }
        result = searcher.searchAfter(before, query, perPage);
        return result;
    }
}
