package com.modsen.api.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.modsen.*")
public class AppConfig implements WebMvcConfigurer {
    public static void main(String[] args){
        SpringApplication.run(AppConfig.class, args);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jacksonConverter =
                new MappingJackson2HttpMessageConverter();
        jacksonConverter.setDefaultCharset(StandardCharsets.UTF_16);
        converters.add(jacksonConverter);
    }

    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames("errorMessage");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
