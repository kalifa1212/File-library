package com.hforge.model;

import java.time.Instant;
import java.util.UUID;

public class FileVersion {


    private UUID id;


    private UUID fileId;


    private Integer version;


    private String storedName;


    private String storagePath;


    private String checksum;


    private Long size;


    private Instant createdAt;


    private String createdBy;


}