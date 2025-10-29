package com.znv.manage.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
@Slf4j
public class RestTemplateConfiguration {

    @Bean("restTemplate")
    public RestTemplate restTemplate(@Qualifier("simpleFactory") ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    /**
     * 跳过ssl验证的restTemplate
     *
     * @return org.springframework.web.client.RestTemplate
     */
    @Bean("unSslRestTemplate")
    public RestTemplate unSslRestTemplate(@Qualifier("unSslFactory") HttpComponentsClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean("simpleFactory")
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30 * 1000);
        factory.setConnectTimeout(30 * 1000);
        return factory;
    }

    @Bean("unSslFactory")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = (chain, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            HttpClientBuilder clientBuilder = HttpClients.custom();
            CloseableHttpClient httpClient = clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            factory.setReadTimeout(30 * 1000);
            factory.setConnectTimeout(30 * 1000);
            return factory;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("unSSLRestTemplate bean create error：" + e.getMessage());
        }
    }
}