package com.hforge.services;

import com.hforge.dto.ProcessedFile;
import com.hforge.enums.FileType;
import com.hforge.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileProcessor {
    boolean supports(FileType type);


    FileInfo process(
            MultipartFile file
    );
    FileInfo thumnailGeneration(
            FileInfo fileInfo
    );
}
