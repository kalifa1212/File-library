package com.hforge.handlers;

import com.hforge.exceptions.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder //TODO Decommenté si probleme survient
public class ErrorDto {
        private Integer httpcode;
        private ErrorCodes code;
        private String message;
        private List<String> errors = new ArrayList<>();

}
