package com.znv.manage.configuration;

import com.znv.manage.common.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate httpsRestTemplate(HttpComponentsClientHttpRequestFactory httpsFactory) {
        RestTemplate restTemplate = new RestTemplate(httpsFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) {

            }
        });
        return restTemplate;
    }

    @Bean(name = "httpsFactory")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() throws Exception {
        CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
        HttpComponentsClientHttpRequestFactory httpsFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        httpsFactory.setReadTimeout(30000);
        httpsFactory.setConnectTimeout(30000);
        return httpsFactory;
    }
}
