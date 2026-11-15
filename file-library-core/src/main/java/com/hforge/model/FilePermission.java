package com.hforge.model;

import com.hforge.enums.PermissionType;

import java.time.Instant;
import java.util.UUID;

public class FilePermission {


    private UUID id;


    private UUID fileId;


    private String principal;


    private PermissionType permission;


    private Instant grantedAt;


    private Instant expiresAt;


}