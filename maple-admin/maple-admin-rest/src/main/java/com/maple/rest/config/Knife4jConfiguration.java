package com.maple.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/6/28
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "adminManage")
    public Docket adminManage() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("笑小枫管理平台接口")
                        .description("笑小枫管理平台接口")
                        .termsOfServiceUrl("https://www.xiaoxiaofeng.site/api/")
                        .contact(new Contact("笑小枫", "https://www.xiaoxiaofeng.site", "zfzjava@163.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("管理平台接口")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.maple.rest.controller.manage"))
                .paths(PathSelectors.any())
                .build();
    }
}
