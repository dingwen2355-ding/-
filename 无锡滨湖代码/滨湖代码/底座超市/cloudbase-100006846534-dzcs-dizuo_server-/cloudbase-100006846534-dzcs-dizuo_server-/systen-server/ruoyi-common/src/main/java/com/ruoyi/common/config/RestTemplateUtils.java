package com.ruoyi.common.config;

import com.ruoyi.common.vo.CommonRes;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class RestTemplateUtils {

    private final RestTemplate restTemplate;

    public RestTemplateUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType);
        return responseEntity.getBody();
    }

    public <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType, uriVariables);
        return responseEntity.getBody();
    }

    public <T> T get(String url, Class<T> responseType, MultiValueMap<String, String> queryParams) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType, queryParams);
        return responseEntity.getBody();
    }

    public <T> T post(String url, Object request, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, responseType);
        return responseEntity.getBody();
    }

    public <T> T post(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, responseType, uriVariables);
        return responseEntity.getBody();
    }
    public CommonRes uploadFile(String url, MultipartFile file) throws IOException {
        // 设置请求body
        MultiValueMap map= new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        map.add("multipartFile", resource);

        // 设置请求header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity requestEntity = new HttpEntity<>(map, headers);

        // 发送请求
        ResponseEntity<CommonRes> responseEntity = restTemplate.postForEntity(url, requestEntity, CommonRes.class);
        CommonRes body = responseEntity.getBody();
        return body;
    }

    public CommonRes uploadFile(String url, String filePath) throws IOException {

        FileSystemResource file = new FileSystemResource(new File(filePath));
        MultiValueMap params = new LinkedMultiValueMap<>();
        params.add("multipartFile", file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity requestEntity =
                new HttpEntity(params, headers);
        // 发送请求
        ResponseEntity<CommonRes> responseEntity = restTemplate.postForEntity(url, requestEntity, CommonRes.class);
        CommonRes body = responseEntity.getBody();
        return body;
    }

    public <T> T post(String url, Object request, Class<T> responseType, MultiValueMap<String, String> queryParams) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType, queryParams);
        return responseEntity.getBody();
    }
    public <T> T post(String url, Class<T> responseType, MultiValueMap<String, String> queryParams,MultiValueMap<String,String> headers) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(queryParams,headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, responseType);
        return responseEntity.getBody();
    }
    public <T> T get(String url, Object request, Class<T> responseType,  HttpHeaders headers) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return responseEntity.getBody();
    }

    public <T> T postJson(String url, Class<T> responseType, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
        return responseEntity.getBody();
    }
    public <T> T postJson(String url, Class<T> responseType, String json,HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
        return responseEntity.getBody();
    }

    public String postJson(String url, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(json, headers);
        return restTemplate.postForEntity(url, request, String.class).getBody();
    }

    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
        return responseEntity.getBody();
    }

    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Object... uriVariables) {
        return restTemplate.execute(url, method, requestCallback, responseExtractor, uriVariables);
    }
}

