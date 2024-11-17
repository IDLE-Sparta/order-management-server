package com.sparta.ordermanagement.framework.persistence.repository.user;

import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserStringId(String userStringId);
    Boolean existsByUserStringId(String userStringId);
    @Modifying
    @Query("UPDATE p_user u SET u.role = :role WHERE u.userStringId = :userStringId")
    Integer updateByUserStringId(@Param("userStringId")String userStringId, @Param("role") Role role);
    int deleteByUserStringId(String userStringId);
}
