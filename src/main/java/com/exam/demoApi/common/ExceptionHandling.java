package com.exam.demoApi.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exam.demoApi.exception.CustomException;
import com.exam.demoApi.exception.ExceptionCode;
import com.exam.demoApi.model.DefaultInfo;

/**
 * @author yunsung Kim
 */
@ControllerAdvice
@RestController
public class ExceptionHandling {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected DefaultInfo handleError(CustomException e) {
        return DefaultInfo.builder()
            .status(e.getResultCode().name())
            .message(e.getResultCode().getMsg())
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected DefaultInfo handleError(Exception e) {
        return DefaultInfo.builder()
            .status(ExceptionCode.FAIL.name())
            .message(e.getMessage())
            .build();
    }
}
