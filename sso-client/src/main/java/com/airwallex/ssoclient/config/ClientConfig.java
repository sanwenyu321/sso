package com.airwallex.ssoclient.config;

import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.client.HttpClient;

@Configuration
public class ClientConfig {
    @Bean
    public HttpClient getClient() {
        return HttpClients.createDefault();
    }
}
