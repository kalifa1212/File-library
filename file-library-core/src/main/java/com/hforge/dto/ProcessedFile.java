package com.hforge.dto;

import com.hforge.enums.FileType;
import com.hforge.enums.StorageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ProcessedFile {

    private InputStream inputStream;
    private String storeName;
    private String path;

    private Boolean overwrite;
}