package com.airwallex.server1.config;

import com.airwallex.ssoclient.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new LoginFilter());
        registrationBean.addInitParameter("loginUrl", "http://localhost:8080");

        return registrationBean;
    }
}
