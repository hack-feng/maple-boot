package com.maple.common.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 上传本地配置
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 */
@Data
@Configuration
public class LocalFileProperties {

    // ---------------本地文件配置 start------------------
    /**
     * 图片存储路径
     */
    @Value("${file.imageFilePath}")
    private String imageFilePath;

    /**
     * 文档存储路径
     */
    @Value("${file.docFilePath}")
    private String docFilePath;

    /**
     * 文件限制大小
     */
    @Value("${file.maxFileSize}")
    private long maxFileSize;
    // --------------本地文件配置 end-------------------

}
