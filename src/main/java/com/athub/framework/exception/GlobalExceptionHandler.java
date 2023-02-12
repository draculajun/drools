package com.athub.framework.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Wang wenjun
 * 全局异常捕捉
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Map<String, Object> exceptionHandler(BusinessException exception) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", exception.getExceptionCode());
        map.put("message", exception.getExceptionMessage());
        log.error(exception.getMessage());
        return map;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

}
