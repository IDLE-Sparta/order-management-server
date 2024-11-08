package com.sparta.ordermanagement.framework.persistence.entity;

import com.sparta.ordermanagement.application.domain.Test;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TestEntity {

    @Id
    private Long id;

    public static TestEntity from(Test test) {
        return new TestEntity(test.getId());
    }
}
