package com.sparta.ordermanagement.framework.persistence.adapter.user;

import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.output.user.UserOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import com.sparta.ordermanagement.framework.persistence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserPersistenceAdapter implements UserOutputPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUserStringId(String userStringId) {
        return userRepository.findByUserStringId(userStringId)
                .map(UserEntity::toDomain)
                .or(Optional::empty);
    }

    public Optional<UserEntity> findByUserId(String userId) {
        return userRepository.findByUserStringId(userId)
                .or(Optional::empty);
    }

    @Override
    public Boolean existsByUserStringId(String userStringId) {
        return userRepository.existsByUserStringId(userStringId);
    }

    @Override
    public String saveUser(User user, String encodedPassword) {
        UserEntity userEntity = UserEntity.from(user, encodedPassword);

        return userRepository.save(userEntity).getUserStringId();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    @Override
    public Integer updateUserById(String userStringId, Role role) {
        return userRepository.updateByUserStringId(userStringId, role);
    }

    @Override
    public int deleteUserByUserStringId(String userStringId) {
        return userRepository.deleteByUserStringId(userStringId);
    }
}
