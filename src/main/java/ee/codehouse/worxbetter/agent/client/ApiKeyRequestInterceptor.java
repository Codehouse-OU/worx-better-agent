package ee.codehouse.worxbetter.agent.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ApiKeyRequestInterceptor implements RequestInterceptor {
    private static final String AUTH_HEADER = "X-APP-ID";
    private final String appId;

    public ApiKeyRequestInterceptor(String appId) {
        this.appId = appId;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(AUTH_HEADER, appId);
    }
}
