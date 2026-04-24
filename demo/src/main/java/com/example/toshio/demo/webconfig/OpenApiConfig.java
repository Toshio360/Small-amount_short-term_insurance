package com.example.toshio.demo.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.example.toshio.client.api.DefaultApi;
import com.example.toshio.client.invoker.ApiClient;

public class OpenApiConfig {

    private final RestTemplate restTemplate;

    public OpenApiConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @org.springframework.beans.factory.annotation.Value("${client.baseUrl}")
    private String clientBaseUrl;

    @Bean
    public DefaultApi defaultApi() {
        ApiClient client = new ApiClient(restTemplate);

        // Eureka のサービス名を BasePath に設定
        client.setBasePath(clientBaseUrl);

        return new DefaultApi(client);
    }

}
