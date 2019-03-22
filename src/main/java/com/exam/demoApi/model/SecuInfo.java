package com.exam.demoApi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

/**
 * @author yunsung Kim
 */
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class SecuInfo {

    private String accessToken;
}
