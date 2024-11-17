package com.sparta.ordermanagement.application.output.user;


import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;

import java.util.List;
import java.util.Optional;

public interface UserOutputPort {

    Optional<User> findByUserStringId(String userStringId);
    Boolean existsByUserStringId(String userStringId);
    String saveUser(User user, String encodedPassword);
    List<User> findAll();
    Integer updateUserById(String userStringId, Role role);
    int deleteUserByUserStringId(String userStringId);
}
