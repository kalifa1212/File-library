package com.hforge.dto;

import com.hforge.exceptions.ErrorCodes;
import lombok.Data;

import java.util.List;

@Data
public class ValidationResultDto {


    private Boolean valid;


    private ErrorCodes errorCode;


    private String message;


    private List<String> violations;


}