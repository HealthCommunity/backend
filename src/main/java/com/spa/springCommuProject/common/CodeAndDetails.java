package com.spa.springCommuProject.common;


import com.spa.springCommuProject.common.exception.NotFoundErrorCodeException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.NoHandlerFoundException;

@AllArgsConstructor
@Getter
public enum CodeAndDetails {
    SUCCESS("20000", "성공", null),
    NOT_FOUND_API("0002", "해당 경로에 대한 응답 API를 찾을 수 없습니다.", NoHandlerFoundException.class),
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
