package com.ssafy.chuck.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
 
@Configuration
public class RestfulConfig {
 
//    @Value("${restTemplate.factory.readTimeout}")
    private int READ_TIMEOUT = 30000;
    
//    @Value("${restTemplate.factory.connectTimeout}")
    private int CONNECT_TIMEOUT = 3000;
    
//    @Value("${restTemplate.httpClient.maxConnTotal}")
    private int MAX_CONN_TOTAL = 100;
    
//    @Value("${restTemplate.httpClient.maxConnPerRoute}")
    private int MAX_CONN_PER_ROUTE = 5;
    
    @Bean
    public RestTemplate restTemplate() {
        
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(READ_TIMEOUT); 
        factory.setConnectTimeout(CONNECT_TIMEOUT); 
        
        HttpClient httpClient = HttpClientBuilder.create() 
                .setMaxConnTotal(MAX_CONN_TOTAL) 
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE) 
                .build();
 
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
 
        return restTemplate;
    }
}
