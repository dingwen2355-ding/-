package com.ruoyi.common.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@Configuration
public class RestTemplateConfig {

    @Value("${restTemplate.connectTimeout}")
    private Integer connectTimeout;

    @Value("${restTemplate.readTimeout}")
    private Integer readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        int maxConnectionsPerRoute = 100;
        int maxTotalConnections = 100;
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(maxTotalConnections)
                .setMaxConnPerRoute(maxConnectionsPerRoute)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        
        return new RestTemplate(requestFactory);
    }
}
