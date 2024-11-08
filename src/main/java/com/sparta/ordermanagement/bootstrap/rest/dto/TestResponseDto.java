package com.sparta.ordermanagement.bootstrap.rest.dto;

import com.sparta.ordermanagement.application.domain.Test;

public class TestResponseDto {

    public static TestResponseDto from(Test test) {
        return new TestResponseDto();
    }
}
