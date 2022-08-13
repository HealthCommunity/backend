package com.spa.springCommuProject.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL) // check
public class CommonResponse<T> {

    private String status;
    private String message;
    private T data;

    public static <T> CommonResponse<T> from(T data) {
        return new CommonResponse<>("200", "성공", data);
    }

    public static CommonResponse<?> from(Exception e) {
        CodeAndDetails exceptionData = CodeAndDetails.findByClass(e.getClass());
        if(exceptionData.getType().equals(IllegalStateException.class)){
            return new CommonResponse<>(exceptionData.getCode(), e.getMessage(), exceptionData.getType());
        }
        return new CommonResponse<>(exceptionData.getCode(), exceptionData.getMessage(), exceptionData.getType());
    }

}
