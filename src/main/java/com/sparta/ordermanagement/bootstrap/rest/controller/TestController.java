package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.Test;
import com.sparta.ordermanagement.application.service.TestService;
import com.sparta.ordermanagement.bootstrap.rest.dto.TestRequestDto;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class TestController {

    private final TestService testService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/test")
    public void test(@RequestBody TestRequestDto requestDto) {
        Test test = requestDto.toDomain();
        long id = testService.createSample(test);
    }
}
