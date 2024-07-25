package com.maple.website.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCommonException;
import com.maple.common.model.IdNumList;
import com.maple.common.util.JwtUtil;
import com.maple.common.util.LocalFileUtil;
import com.maple.common.util.TransformUtils;
import com.maple.website.bean.*;
import com.maple.website.common.enums.DataTypeEnum;
import com.maple.website.common.enums.DownloadTypeEnum;
import com.maple.website.mapper.*;
import com.maple.website.service.IWebArticleService;
import com.maple.website.service.IWebUserOperationService;
import com.maple.website.vo.home.HomeCategory;
import com.maple.website.vo.home.HomeCategoryItem;
import com.maple.website.vo.home.HomeData;
import com.maple.website.vo.model.WebArticleModel;
import com.maple.website.vo.model.WebCategoryModel;
import com.maple.website.vo.model.WebContentModel;
import com.maple.website.vo.model.WebUserOperationModel;
import com.maple.website.vo.query.WebArticlePageQuery;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.maple.common.config.MapleConstants.LAST_LIMIT_1;

/**
 * 网站文章Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebArticleServiceImpl extends ServiceImpl<WebArticleMapper, WebArticle> implements IWebArticleService {

    private final WebArticleMapper webArticleMapper;

    private final WebContentMapper webContentMapper;

    private final WebUserOperationMapper userOperationMapper;

    private final WebCategoryMapper categoryMapper;

    private final WebUserMapper userMapper;

    private final IWebUserOperationService userOperationService;

    private final LocalFileUtil localFileUtil;

    @Override
    public IPage<WebArticleModel> getPageList(WebArticlePageQuery query) {
        if (StringUtils.isNotBlank(query.getQuery().getMenuPath())) {
            List<Long> categoryList = categoryMapper.getCategoryByMenuPath(query.getQuery().getMenuPath());
            if (categoryList.isEmpty()) {
                categoryList = new ArrayList<>();
                categoryList.add(-1L);
            }
            query.getQuery().setCategoryIdList(categoryList);
        }

        return webArticleMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebArticleModel getWebArticleById(Long id, Boolean isWebUser) {
        WebArticleModel articleModel = getTitleInfoById(id, isWebUser);
        if (Objects.isNull(articleModel) || Objects.isNull(articleModel.getId())) {
            return articleModel;
        }
        if (BooleanUtils.isTrue(isWebUser)) {
            webArticleMapper.update(null, Wrappers.lambdaUpdate(WebArticle.class)
                    .eq(WebArticle::getId, articleModel.getId())
                    .set(WebArticle::getReadNum, articleModel.getReadNum() + 1));
            if (Objects.nonNull(JwtUtil.getUserId())) {
                userOperationService.read(articleModel.getId(), articleModel.getDataType());
            }
        }
        return articleModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createWebArticle(WebArticleModel model) {
        WebArticle webArticle = TransformUtils.map(model, WebArticle.class);
        if (Objects.isNull(webArticle.getSortNum())) {
            webArticle.setSortNum(webArticleMapper.getMaxSortNum(webArticle.getCategoryId()));
        }
        webArticleMapper.insert(webArticle);

        WebContent content = TransformUtils.map(model.getContentModel(), WebContent.class);
        content.setArticleId(webArticle.getId());
        webContentMapper.insert(content);
        return webArticle.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebArticle(WebArticleModel model) {
        webArticleMapper.updateById(TransformUtils.map(model, WebArticle.class));
        webContentMapper.updateById(TransformUtils.map(model.getContentModel(), WebContent.class));
    }

    @Override
    public Integer deleteWebArticle(Long id) {
        WebArticle article = webArticleMapper.selectById(id);
        article.setStatus(4);
        return webArticleMapper.updateById(article);
    }

    @Override
    public WebArticleModel getById(Long id) {
        return TransformUtils.map(webArticleMapper.selectById(id), WebArticleModel.class);
    }

    @Override
    public HomeData getHomeData() {
        List<HomeCategory> homeCategoryList = new ArrayList<>();
        List<WebCategory> parentCategories = categoryMapper.selectList(Wrappers.lambdaQuery(WebCategory.class)
                .eq(WebCategory::getIsValid, true)
                .eq(WebCategory::getIsHot, true)
                .eq(WebCategory::getParentId, 0)
                .orderByDesc(WebCategory::getIsTop)
                .orderByAsc(WebCategory::getSortNum)
                .orderByDesc(WebCategory::getId));
        if (CollectionUtils.isNotEmpty(parentCategories)) {
            List<WebCategory> itemCategories = categoryMapper.selectList(Wrappers.lambdaQuery(WebCategory.class)
                    .eq(WebCategory::getIsValid, true)
                    .in(WebCategory::getParentId, parentCategories.stream().map(WebCategory::getId).collect(Collectors.toList()))
                    .orderByDesc(WebCategory::getIsTop)
                    .orderByAsc(WebCategory::getSortNum)
                    .orderByDesc(WebCategory::getId));
            if (CollectionUtils.isNotEmpty(itemCategories)) {
                Map<Long, List<WebCategory>> categoryMap = itemCategories.stream().collect(Collectors.groupingBy(WebCategory::getParentId));
                List<IdNumList> idNumLists = categoryMapper.selectCountGroupByCategory();
                Map<Long, Long> categoryNumMap = idNumLists.stream().collect(Collectors.toMap(IdNumList::getId, IdNumList::getNum));

                for (WebCategory category : parentCategories) {
                    List<WebCategory> list = categoryMap.get(category.getId());
                    if (CollectionUtils.isEmpty(list)) {
                        continue;
                    }
                    List<HomeCategoryItem> items = new ArrayList<>();
                    for (WebCategory blogCategory : list) {
                        if (Objects.isNull(categoryNumMap.get(blogCategory.getId()))) {
                            continue;
                        }
                        HomeCategoryItem item = new HomeCategoryItem();
                        item.setId(blogCategory.getId());
                        item.setTitle(blogCategory.getName());
                        item.setImage(blogCategory.getIcon());
                        item.setDescription(blogCategory.getDescription());
                        item.setSubtitle(categoryNumMap.get(blogCategory.getId()) + "篇");
                        item.setRoute(category.getUrl());
                        items.add(item);
                    }
                    HomeCategory homeCategory = new HomeCategory();
                    homeCategory.setHeading(category.getName());
                    homeCategory.setDescription(category.getDescription());
                    homeCategory.setItems(items);
                    homeCategoryList.add(homeCategory);
                }
            }
        }
        return HomeData.builder()
                .articleNum(webArticleMapper.selectCount(Wrappers.lambdaQuery(WebArticle.class).eq(WebArticle::getDataType, DataTypeEnum.ARTICLE.getCode()).eq(WebArticle::getStatus, 1)))
                .resourceNum(webArticleMapper.selectCount(Wrappers.lambdaQuery(WebArticle.class).eq(WebArticle::getDataType, DataTypeEnum.RESOURCE.getCode()).eq(WebArticle::getStatus, 1)))
                .linksNum(webArticleMapper.selectCount(Wrappers.lambdaQuery(WebArticle.class).eq(WebArticle::getDataType, DataTypeEnum.LINKS.getCode()).eq(WebArticle::getStatus, 1)))
                .homeCategoryList(homeCategoryList)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void downResource(HttpServletResponse response, WebArticleModel articleModel) {
        WebUser user = userMapper.selectById(JwtUtil.getUserId());
        if (Objects.isNull(user)) {
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "没有找到用户信息");
        }
        WebArticle resource = webArticleMapper.selectById(articleModel.getId());
        if (Objects.isNull(resource) || resource.getStatus() != 1) {
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "没有找到资源信息");
        }
        resource.setDownloadNum(resource.getDownloadNum() + 1);
        webArticleMapper.updateById(resource);
        WebUserOperationModel userOperation = new WebUserOperationModel();
        userOperation.setDataId(resource.getId());
        userOperationService.download(userOperation, DataTypeEnum.RESOURCE.getCode());
        String[] elements = resource.getOriginalUrl().split("/");
        if (Objects.equals(DownloadTypeEnum.LOCAL.getCode(), resource.getDataKind())) {
            localFileUtil.downLoadFile(response, elements[elements.length - 1]);
        } else if (Objects.equals(resource.getDataKind(), DownloadTypeEnum.NET.getCode())) {
            ServletOutputStream out;
            try {
                //与服务器建立连接
                URL url = new URL(resource.getOriginalUrl());
                URLConnection conn = url.openConnection();
                InputStream inputStream = conn.getInputStream();
                String filename = URLEncoder.encode(elements[elements.length - 1], "utf-8");
                //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
                response.setContentType("application/octet-stream");
                response.setHeader("content-Disposition", "attachment; filename=" + filename);

                out = response.getOutputStream();
                // 读取文件流
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.close();
                inputStream.close();
            } catch (Exception e) {
                log.error("读取在线文件失败：", e);
                throw new MapleCommonException(ErrorCode.COMMON_ERROR, "下载失败，请重试");
            }
        }
    }

    public WebArticleModel getTitleInfoById(Long id, Boolean isWebUser) {
        WebArticle article = webArticleMapper.selectById(id);
        WebContent content = webContentMapper.selectOne(Wrappers.lambdaQuery(WebContent.class)
                .eq(WebContent::getArticleId, id));
        if (Objects.isNull(article)) {
            return new WebArticleModel();
        }

        WebArticleModel articleModel = TransformUtils.map(article, WebArticleModel.class);
        if (Objects.nonNull(article.getRelevanceArticleId())) {
            WebContent relevanceContent = webContentMapper.selectOne(Wrappers.lambdaQuery(WebContent.class)
                    .eq(WebContent::getArticleId, article.getRelevanceArticleId()));
            if (Objects.nonNull(relevanceContent)) {
                articleModel.setRelevanceContent(relevanceContent.getOriginalContent());
            }
        }
        if (BooleanUtils.isTrue(isWebUser)) {
            WebUserOperation operation = userOperationMapper.selectOne(Wrappers.lambdaQuery(WebUserOperation.class)
                    .eq(WebUserOperation::getDataId, id)
                    .eq(WebUserOperation::getDataType, DataTypeEnum.ARTICLE.getCode())
                    .eq(WebUserOperation::getUserId, JwtUtil.getUserId())
                    .last(LAST_LIMIT_1));
            if (Objects.nonNull(operation)) {
                articleModel.setIsCollect(operation.getIsCollect());
                articleModel.setIsLike(operation.getIsLike());
            }
            // 获取上一篇、下一篇文章
            articleModel.setPreTitle(webArticleMapper.getPreviousTitle(articleModel));
            articleModel.setNextTitle(webArticleMapper.getNextTitle(articleModel));
        }
        articleModel.setContentModel(TransformUtils.map(content, WebContentModel.class));
        WebCategory blogCategory = categoryMapper.selectById(article.getCategoryId());
        articleModel.setCategoryModel(TransformUtils.map(blogCategory, WebCategoryModel.class));
        return articleModel;
    }

}
