package com.hforge.config;

import com.hforge.services.StorageService;
import com.hforge.storageServices.LocalStorageService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(StorageProperties.class)
public class LocalStorageAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public StorageService storageService(StorageProperties properties) {
        return new LocalStorageService(properties);
    }
}
