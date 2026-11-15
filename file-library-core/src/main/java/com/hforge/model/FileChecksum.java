package com.hforge.model;

import com.hforge.enums.HashAlgorithm;

import java.time.Instant;
import java.util.UUID;

public class FileChecksum {


    private UUID id;


    private UUID fileId;


    private HashAlgorithm algorithm;


    private String value;


    private Boolean verified;


    private Instant verifiedAt;


}