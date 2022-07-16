package com.spa.springCommuProject.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.spa.springCommuProject.common.exception.HealthException;
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
        return new CommonResponse<>(CodeAndDetails.SUCCESS.getCode(), CodeAndDetails.SUCCESS.getMessage(), data);
    }

    public static CommonResponse<?> from(Exception e) {
        CodeAndDetails exceptionData = CodeAndDetails.findByClass(e.getClass());
        return new CommonResponse<>(exceptionData.getCode(), exceptionData.getMessage(), exceptionData.getType());
    }

}
