package com.hforge.services;

import com.hforge.enums.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileTypeDetector {
    FileType detect(MultipartFile file);
    String mimeType(MultipartFile file) throws IOException;
}
