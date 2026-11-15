package com.hforge.model;

import com.hforge.enums.FileStatus;
import com.hforge.enums.FileType;
import com.hforge.enums.StorageType;
import com.hforge.enums.Visibility;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class FileInfo {

    private UUID id;


    private String originalName;


    private String storedName;


    private String extension;


    private String mimeType;


    private Long size;


    private String storagePath;


    private String publicUrl;

    private String ThumbnailPath;


    private StorageType storageType;


    private FileType fileType;


    private FileStatus status;


    private Visibility visibility;


    private String checksum;


    private Instant createdAt;


    private Instant updatedAt;


}