package com.sparta.ordermanagement.bootstrap.rest.dto;

import com.sparta.ordermanagement.application.domain.Test;

public class TestRequestDto {

    public Test toDomain() {
        return new Test();
    }
}
