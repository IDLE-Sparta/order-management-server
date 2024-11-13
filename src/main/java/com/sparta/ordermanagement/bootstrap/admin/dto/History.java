package com.sparta.ordermanagement.bootstrap.admin.dto;

import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class History {

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;

    public static History from(BaseEntity baseEntity) {
        return new History(
            baseEntity.getCreatedAt(), baseEntity.getCreatedBy(),
            baseEntity.getUpdatedAt(), baseEntity.getUpdatedBy(),
            baseEntity.getDeletedAt(), baseEntity.getDeletedBy()
        );
    }
}
