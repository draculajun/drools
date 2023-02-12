package com.athub.framework.intercepter;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截header，存放在RequestHeaderContext
public class RequestHeaderContextInterceptor extends HandlerInterceptorAdapter {

    private static final String APPID = "appId";
    private static final String TOKEN = "token";
    private static final String ORGID = "orgId";
    private static final String SITES = "sites";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        initHeaderContext(request);
        return super.preHandle(request, response, handler);
    }

    private void initHeaderContext(HttpServletRequest request) {
        new RequestHeaderContext.RequestHeaderContextBuild().token(request.getHeader(TOKEN)).appId(request.getHeader(APPID))
                .orgId(request.getHeader(ORGID)).sites(request.getHeader(SITES)).bulid();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestHeaderContext.clean();
        super.postHandle(request, response, handler, modelAndView);
    }
}
