package com.hforge.config;

import com.hforge.processor.ImageProcessor;
import com.hforge.services.FileProcessor;
import com.hforge.services.FileTypeDetector;
import com.hforge.services.NamingStrategyService;
import com.hforge.services.impl.FileTypeDetectorImpl;
import com.hforge.services.impl.NamingStrategyServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ImageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "namingStrategyService")
    public NamingStrategyService namingStrategyService( ) {
        return new NamingStrategyServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = "imageProcessor")
    public FileProcessor imageProcessor(NamingStrategyService namingStrategyService,FileTypeDetector fileTypeDetector) {
        return new ImageProcessor(namingStrategyService,fileTypeDetector);
    }
}
