package com.hforge.config;

import com.hforge.Services.VideoToolsService;
import com.hforge.Services.impl.VideoToolsServiceImpl;
import com.hforge.processor.VideosProcessor;
import com.hforge.services.FileProcessor;
import com.hforge.services.FileTypeDetector;
import com.hforge.services.NamingStrategyService;
import com.hforge.services.impl.FileTypeDetectorImpl;
import com.hforge.services.impl.NamingStrategyServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class VideoAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = "namingStrategyService")
    public NamingStrategyService namingStrategyService( ) {
        return new NamingStrategyServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = "namingStrategyService")
    public FileTypeDetector fileTypeDetector( ) {
        return new FileTypeDetectorImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = "videoToolsService")
    public VideoToolsService videoToolsService( ) {
        return new VideoToolsServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = "videosProcessor")
    public VideosProcessor videosProcessor(NamingStrategyService namingStrategyService, FileTypeDetector fileTypeDetector, VideoToolsService videoToolsService) {
        return new VideosProcessor(namingStrategyService,fileTypeDetector,videoToolsService);
    }
}
