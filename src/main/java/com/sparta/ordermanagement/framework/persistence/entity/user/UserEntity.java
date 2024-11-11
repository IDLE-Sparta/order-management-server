package com.sparta.ordermanagement.framework.persistence.entity.user;

import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_user")
public class UserEntity extends BaseEntity {

    @Id
    @Column(name ="user_id")
    private String id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private Role role;

    @JoinColumn(name = "region_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity regionEntity;
}
