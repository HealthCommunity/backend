package com.spa.springCommuProject.common;


import com.spa.springCommuProject.common.exception.LoginException;
import com.spa.springCommuProject.common.exception.NotFoundErrorCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CodeAndDetails {
    NOT_FOUND_API("0001", "해당 경로에 대한 응답 API를 찾을 수 없습니다.", NoHandlerFoundException.class),
    JOIN_ERROR("0002", "회원가입에러", IllegalStateException.class),
    LOGIN_ERROR("0003", "아이디 또는 비밀번호가 잘못되었습니다.", LoginException.class)
    ;

    private final String code;
    private final String message;
    private final Class<? extends Exception> type;

    public static CodeAndDetails findByClass(Class<? extends Exception> type) {
        return Arrays.stream(CodeAndDetails.values())
            .filter(code -> code.type.equals(type))
            .findAny()
            .orElseThrow(NotFoundErrorCodeException::new);
    }
}
