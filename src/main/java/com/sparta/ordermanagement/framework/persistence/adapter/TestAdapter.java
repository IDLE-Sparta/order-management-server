package com.sparta.ordermanagement.framework.persistence.adapter;

import com.sparta.ordermanagement.application.domain.Test;
import com.sparta.ordermanagement.application.output.TestOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.TestEntity;
import com.sparta.ordermanagement.framework.persistence.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestAdapter implements TestOutputPort {

    private final TestRepository testRepository;

    @Override
    public long createTest(Test test) {
        TestEntity testEntity = TestEntity.from(test);
        return testRepository.save(testEntity).getId();
    }
}
