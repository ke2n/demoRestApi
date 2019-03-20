/*
 * Copyright (c) 2018. 10. 30.
 * Yunsung Kim
 */

package com.exam.demoApi.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    NOT_FOUND_REGION("존재 하지 않는 REGION 입니다."),
    NOT_FOUND_SUPPORT_ID("존재 하지 않는 SUPPORT_ID 입니다."),
    NOT_FOUND_DATA("결과값이 없습니다.");

    private String msg;

    ExceptionCode(String msg) {
        this.msg = msg;
    }

}
