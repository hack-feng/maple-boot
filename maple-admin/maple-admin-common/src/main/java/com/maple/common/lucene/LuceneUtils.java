package com.maple.common.lucene;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zhangfuzeng
 * @date 2024/1/8
 */
@Slf4j
@Component
public class LuceneUtils {

    @Value("${file.lucenePath}")
    private String lucenePath;

    public String initLuceneData(List<LuceneDataModel> list) {

        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        for (LuceneDataModel dataModel : list) {
            // 创建文档对象
            Document document = new Document();
            // StringField: 这个 Field 用来构建一个字符串Field，不分析，会索引，Field.Store控制存储
            // LongPoint、IntPoint 等类型存储数值类型的数据。会分析，会索引，不存储，如果想存储数据还需要使用 StoredField
            // StoredField: 这个 Field 用来构建不同类型，不分析，不索引，会存储
            // TextField: 如果是一个Reader, 会分析，会索引，，Field.Store控制存储
            if (dataModel.getType() == 0) {
                // 目录特殊处理下吧，不然会跟文章ID重复
                document.add(new StoredField("id", "M" + dataModel.getId()));
            } else {
                document.add(new StoredField("id", dataModel.getId()));
            }
            document.add(new StoredField("imageUrl", StringUtils.isBlank(dataModel.getImageUrl()) ? "" : dataModel.getImageUrl()));
            document.add(new StoredField("originalUrl", StringUtils.isBlank(dataModel.getOriginalUrl()) ? "" : dataModel.getOriginalUrl()));
            document.add(new StringField("type", String.valueOf(dataModel.getType()), Field.Store.YES));
            document.add(new TextField("title", dataModel.getTitle(), Field.Store.YES));
            document.add(new TextField("description", StringUtils.isBlank(dataModel.getDescription()) ? "" : dataModel.getDescription(), Field.Store.YES));
            document.add(new TextField("content", StringUtils.isBlank(dataModel.getContent()) ? "" : dataModel.getContent(), Field.Store.NO));
            docs.add(document);
        }
        // 引入IK分词器，如果需要解决上面版本冲突报错的问，使用`new MyIKAnalyzer()`即可
        Analyzer analyzer = new IKAnalyzer(true);
        // 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(lucenePath));
             IndexWriter indexWriter = new IndexWriter(directory, conf)) {
            // 把文档集合交给IndexWriter
            indexWriter.addDocuments(docs);
            // 提交
            indexWriter.commit();
        } catch (Exception e) {
            log.error("创建索引失败", e);
            return "创建索引失败";
        }
        return "创建索引成功";
    }

    public String addLuceneData(LuceneDataModel dataModel) {
        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        // 创建文档对象
        Document document = new Document();
        // StringField: 这个 Field 用来构建一个字符串Field，不分析，会索引，Field.Store控制存储
        // LongPoint、IntPoint 等类型存储数值类型的数据。会分析，会索引，不存储，如果想存储数据还需要使用 StoredField
        // StoredField: 这个 Field 用来构建不同类型，不分析，不索引，会存储
        // TextField: 如果是一个Reader, 会分析，会索引，，Field.Store控制存储
        if (dataModel.getType() == 0) {
            // 目录特殊处理下吧，不然会跟文章ID重复
            document.add(new StoredField("id", "M" + dataModel.getId()));
        } else {
            document.add(new StoredField("id", dataModel.getId()));
        }
        document.add(new StoredField("imageUrl", StringUtils.isBlank(dataModel.getImageUrl()) ? "" : dataModel.getImageUrl()));
        document.add(new StoredField("originalUrl", StringUtils.isBlank(dataModel.getOriginalUrl()) ? "" : dataModel.getOriginalUrl()));
        document.add(new StringField("type", String.valueOf(dataModel.getType()), Field.Store.YES));
        document.add(new TextField("title", dataModel.getTitle(), Field.Store.YES));
        document.add(new TextField("description", StringUtils.isBlank(dataModel.getDescription()) ? "" : dataModel.getDescription(), Field.Store.YES));
        document.add(new TextField("content", StringUtils.isBlank(dataModel.getContent()) ? "" : dataModel.getContent(), Field.Store.NO));
        docs.add(document);
        // 引入IK分词器，如果需要解决上面版本冲突报错的问，使用`new MyIKAnalyzer()`即可
        Analyzer analyzer = new IKAnalyzer(true);
        // 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

        // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(lucenePath));
             IndexWriter indexWriter = new IndexWriter(directory, conf)) {
            // 把文档集合交给IndexWriter
            indexWriter.addDocuments(docs);
            // 提交
            indexWriter.commit();
        } catch (Exception e) {
            log.error("创建索引失败", e);
            return "创建索引失败";
        }
        return "创建索引成功";
    }

    public String updateLuceneData(LuceneDataModel dataModel) {
        // 创建文档对象
        Document document = new Document();
        // StringField: 这个 Field 用来构建一个字符串Field，不分析，会索引，Field.Store控制存储
        // LongPoint、IntPoint 等类型存储数值类型的数据。会分析，会索引，不存储，如果想存储数据还需要使用 StoredField
        // StoredField: 这个 Field 用来构建不同类型，不分析，不索引，会存储
        // TextField: 如果是一个Reader, 会分析，会索引，，Field.Store控制存储
        document.add(new StoredField("id", dataModel.getId()));
        document.add(new StoredField("imageUrl", StringUtils.isBlank(dataModel.getImageUrl()) ? "" : dataModel.getImageUrl()));
        document.add(new StoredField("originalUrl", StringUtils.isBlank(dataModel.getOriginalUrl()) ? "" : dataModel.getOriginalUrl()));
        document.add(new StringField("type", String.valueOf(dataModel.getType()), Field.Store.YES));
        document.add(new TextField("title", dataModel.getTitle(), Field.Store.YES));
        document.add(new TextField("description", StringUtils.isBlank(dataModel.getDescription()) ? "" : dataModel.getDescription(), Field.Store.YES));
        document.add(new TextField("content", StringUtils.isBlank(dataModel.getContent()) ? "" : dataModel.getContent(), Field.Store.NO));
        // 引入IK分词器，如果需要解决上面版本冲突报错的问，使用`new MyIKAnalyzer()`即可
        Analyzer analyzer = new IKAnalyzer(true);
        // 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

        // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(lucenePath));
             IndexWriter indexWriter = new IndexWriter(directory, conf)) {
            // 把文档集合交给IndexWriter
            Term term;
            if (dataModel.getType() == 0) {
                term = new Term("id", "M" + dataModel.getId());
            } else {
                term = new Term("id", String.valueOf(dataModel.getId()));
            }
            indexWriter.updateDocument(term, document);
            // 提交
            indexWriter.commit();
        } catch (Exception e) {
            log.error("修改索引失败", e);
            return "修改索引失败";
        }
        return "修改索引成功";
    }

    public String deleteLuceneData(LuceneDataModel dataModel) {
        Analyzer analyzer = new IKAnalyzer(true);
        // 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(lucenePath));
             IndexWriter indexWriter = new IndexWriter(directory, conf)) {
            // 把文档集合交给IndexWriter
            Term term;
            if (dataModel.getType() == 0) {
                term = new Term("id", "M" + dataModel.getId());
            } else {
                term = new Term("id", String.valueOf(dataModel.getId()));
            }
            indexWriter.deleteDocuments(term);
            // 提交
            indexWriter.commit();
        } catch (Exception e) {
            log.error("删除索引失败", e);
            return "删除索引失败";
        }
        return "删除索引成功";
    }
}
