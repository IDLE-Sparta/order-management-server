package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.Test;
import com.sparta.ordermanagement.application.output.TestOutputPort;
import com.sparta.ordermanagement.framework.persistence.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {

    private final TestOutputPort testOutputPort;

    public long createSample(Test test) {
        return testOutputPort.createTest(test);
    }
}
