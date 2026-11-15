package com.hforge.config;

import com.hforge.processor.ImageProcessor;
import com.hforge.services.*;
import com.hforge.services.impl.FfmpegServiceImpl;
import com.hforge.services.impl.FileServiceImpl;
import com.hforge.services.impl.FileTypeDetectorImpl;
import com.hforge.storageServices.LocalStorageService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AutoConfiguration
@AutoConfigureAfter(LocalStorageAutoConfiguration.class)
public class FileStorageAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public FileTypeDetector fileTypeDetector() {
        return new FileTypeDetectorImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public FfmpegService ffmpegService() {
        return new FfmpegServiceImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public FileService fileService(List<FileProcessor> processors, FileTypeDetector fileTypeDetector,
                                   StorageService storageService,FfmpegService ffmpegService) {
        return new FileServiceImpl(processors,fileTypeDetector,storageService,ffmpegService);
    }
}