package com.maple.rest.config;

import com.maple.common.config.LocalFileProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 王新月 
 * @date 2024/1/10
 */
@Configuration
@AllArgsConstructor
public class LocalFileConfig implements WebMvcConfigurer {

    private final LocalFileProperties localFileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 重写方法
        // 修改tomcat 虚拟映射
        // 定义图片存放路径
        registry.addResourceHandler("/local/images/**").
                addResourceLocations("file:" + localFileProperties.getImageFilePath());
        // 重写方法
        // 修改tomcat 虚拟映射
        // 定义图片存放路径
        registry.addResourceHandler("/fileApi/local/images/**").
                addResourceLocations("file:" + localFileProperties.getImageFilePath());
        //定义文档存放路径
        registry.addResourceHandler("/local/doc/**").
                addResourceLocations("file:" + localFileProperties.getDocFilePath());
        // 重写方法
        // 修改tomcat 虚拟映射
        // 定义图片存放路径
        registry.addResourceHandler("/fileApi/local/doc/**").
                addResourceLocations("file:" + localFileProperties.getImageFilePath());
    }
}
