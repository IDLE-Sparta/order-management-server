package com.sparta.ordermanagement.framework.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
    private String updatedBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;
    private String deletedBy;

}
