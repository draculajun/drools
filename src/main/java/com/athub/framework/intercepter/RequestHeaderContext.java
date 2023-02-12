package com.athub.framework.intercepter;

public class RequestHeaderContext {

    private static final ThreadLocal<RequestHeaderContext> REQUEST_HEADER_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();
    private String appId;
    private String token;
    private String orgId;
    private String sites;

    public String getAppId() {
        return appId;
    }

    public String getToken() {
        return token;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getSites() {
        return sites;
    }

    public static RequestHeaderContext getInstance() {
        return REQUEST_HEADER_CONTEXT_THREAD_LOCAL.get();
    }

    public static void setContext(RequestHeaderContext context) {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.set(context);
    }

    public static void clean() {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.remove();
    }

    private RequestHeaderContext(RequestHeaderContextBuild requestHeaderContextBuild) {
        this.appId = requestHeaderContextBuild.appId;
        this.token = requestHeaderContextBuild.token;
        this.orgId = requestHeaderContextBuild.orgId;
        this.sites = requestHeaderContextBuild.sites;
        setContext(this);
    }

    public static class RequestHeaderContextBuild {
        private String appId;
        private String token;
        private String orgId;
        private String sites;

        public RequestHeaderContextBuild appId(String appId) {
            this.appId = appId;
            return this;
        }

        public RequestHeaderContextBuild token(String token) {
            this.token = token;
            return this;
        }

        public RequestHeaderContextBuild orgId(String orgId) {
            this.orgId = orgId;
            return this;
        }

        public RequestHeaderContextBuild sites(String sites) {
            this.sites = sites;
            return this;
        }

        public RequestHeaderContext bulid() {
            return new RequestHeaderContext(this);
        }
    }

}