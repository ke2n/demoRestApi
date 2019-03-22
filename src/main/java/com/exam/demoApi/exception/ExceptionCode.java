package com.exam.demoApi.exception;

import lombok.Getter;

/**
 * @author yunsung Kim
 */
@Getter
public enum ExceptionCode {
    FAIL("요청에 실패하였습니다."),

    UNAUTHORIZED_REQUEST("허가되지않은 요청입니다."),

    ONLY_SUPPORT_UTF8("UTF8 포맷만 지원합니다."),
    CANNOT_CONVERT_FILE("정상 형식의 파일이 아닙니다: "),
    NOT_FOUND_REGION("존재 하지 않는 자자체명(REGION) 입니다."),
    NOT_FOUND_SUPPORT_ID("존재 하지 않는 ID 입니다."),
    NOT_FOUND_DATA("결과값이 없습니다."),

    NOT_FOUND_USER("해당 유저정보가 존재하지 않습니다."),
    SIGNUP_REQUIRED_USERNAME("등록할 유저이름을 입력해 주세요."),
    SIGNUP_REQUIRED_PASSWORD("등록할 패스워드를 입력해 주세요."),
    SIGNUP_EXIST_USERNAME("이미 등록된 유저이름 입니다."),
    ;

    private String msg;

    ExceptionCode(String msg) {
        this.msg = msg;
    }

}
