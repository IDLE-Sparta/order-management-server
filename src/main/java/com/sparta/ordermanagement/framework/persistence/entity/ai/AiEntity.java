package com.sparta.ordermanagement.framework.persistence.entity.ai;

import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_ai")
public class AiEntity extends BaseEntity {

    @Id
    @Column(name = "ai_id")
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String requestContent;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String responseContent;

}
