package com.maple.common.config;

import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleBaseException;
import com.maple.common.model.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 异常信息统一处理
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/20
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 自定义异常处理
     *
     * @param e 异常信息
     * @return 返回结果
     */
    @ExceptionHandler(MapleBaseException.class)
    public ResultJson mapleBaseException(MapleBaseException e) {
        log.error("自定义异常信息 ex={}", e.getMessage(), e);
        setResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ResultJson(e.getCode(), e.getErrorMsg());
    }

    /**
     * 参数校验异常处理
     *
     * @param e 异常信息
     * @return 返回结果
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public ResultJson validException(MethodArgumentNotValidException e) {
        log.error("参数校验异常信息 ex={}", e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p -> {
                FieldError fieldError = (FieldError) p;
                stringBuilder.append(fieldError.getDefaultMessage());
            });
        }
        setResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ResultJson(ErrorCode.PARAM_ERROR.getCode(), stringBuilder.toString());
    }

    /**
     * 系统异常.
     *
     * @param e 异常信息
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @Order(99)
    public ResultJson exception(Exception e) {
        log.error("系统异常信息 ex={}", e.getMessage(), e);
        // 未知异常统一抛出9999
        setResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ResultJson(ErrorCode.OTHER_ERROR.getCode(), ErrorCode.OTHER_ERROR.getMsg());
    }

    private void setResponse (int code) {
        RequestAttributes res = RequestContextHolder.getRequestAttributes();
        if (res instanceof ServletRequestAttributes) {
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().setStatus(code);
        } else {
            HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            resp.setStatus(code);
        }
    }
}
