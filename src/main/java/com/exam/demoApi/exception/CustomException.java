/*
 * Copyright (c) 2018. 10. 30.
 * Yunsung Kim
 */

package com.exam.demoApi.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ExceptionCode resultCode;

    public CustomException(ExceptionCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }
}
