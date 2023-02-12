package com.athub.framework.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

//springcloud fegin获取request header
@Configuration
public class FeginInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, String> headers = getHeaders(getHttpServletRequest());
        if (headers != null) {
            for (String headerName : headers.keySet()) {
                requestTemplate.header(headerName, getHeaders(getHttpServletRequest()).get(headerName));
            }
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            if (RequestContextHolder.getRequestAttributes() != null) {
                return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        if (request != null && request.getHeaderNames() != null) {
            Map<String, String> map = new LinkedHashMap<>();
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
            return map;
        } else {
            return null;
        }
    }

}
