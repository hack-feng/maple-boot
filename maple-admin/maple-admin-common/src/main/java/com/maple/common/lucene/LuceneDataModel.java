package com.maple.common.lucene;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangfuzeng
 * @date 2024/1/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LuceneDataModel {

    /**
     * 数据id
     * 只保存，不检索
     */
    private Long id;

    /**
     * 类型 0:目录 1：文章 2：资源 3：链接
     * 只保存，不检索
     */
    private Integer type;

    /**
     * 标题
     * 保存，检索
     */
    private String title;

    /**
     * 描述
     * 保存，检索
     */
    private String description;

    /**
     * 内容
     * 不保存，检索
     */
    private String content;

    /**
     * 图片链接 只保存，不检索
     */
    private String imageUrl;

    /**
     * 跳转/下载链接 只保存，不检索
     */
    private String originalUrl;
}
