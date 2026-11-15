package com.hforge.dto;

import lombok.Data;

@Data
public class DeleteRequestDto {


    private String fileId;


    private Boolean permanent;


    private Boolean force;


}