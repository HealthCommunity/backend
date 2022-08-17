package com.spa.springCommuProject.common;


import com.spa.springCommuProject.common.exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CodeAndDetails {
    NOT_FOUND_API("0400", "해당 경로에 대한 응답 API를 찾을 수 없습니다.", NoHandlerFoundException.class),
    JOIN_ERROR("0401", "회원가입에러", JoinException.class),
    LOGIN_ERROR("0402", "아이디 또는 비밀번호가 잘못되었습니다.", LoginException.class),
    SELECT_ERROR("0450", "select 종류가 잘못되었습니다.",NotFoundSelectException .class),
    THREEPOWER_ERROR("0451", "동영상이 모두 올라오지 않았습니다", NoThreePowerException.class),
    ELEMENT_ERROR("0452", "빈칸이 있습니다.", NoElementException.class),
    ELSE_ERROR("0500", "기타에러가 발생하였습니다",ElseException .class)
    ;

    private final String code;
    private final String message;
    private final Class<? extends Exception> type;

    public static CodeAndDetails findByClass(Class<? extends Exception> type) {
        return Arrays.stream(CodeAndDetails.values())
            .filter(code -> code.type.equals(type))
            .findAny()
            .orElse(CodeAndDetails.ELSE_ERROR);
    }
}
