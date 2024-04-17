package com.maple.common.config;

import com.alibaba.fastjson.JSON;
import com.maple.common.model.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 对返回结果统一进行处理，包括返回结果格式统一包装
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/8
 */
@Slf4j
@RestControllerAdvice("com.maple")
public class RestResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 返回结果包装统一返回格式
     *
     * @return 包装后的返回结果
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // 指定返回的结果为application/json格式，不指定，String类型转json后返回Content-Type是text/plain;charset=UTF-8
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ResultJson result = new ResultJson(body);
        // 若返回类型是ResultJson，则不进行修改
        if (body == null) {
            if (returnType.getParameterType().isAssignableFrom(String.class)) {
                return JSON.toJSONString(result);
            }
        } else if (body instanceof ResultJson) {
            return body;
        } else if (body instanceof String) {
            return JSON.toJSONString(result);
        }
        return result;
    }
}
