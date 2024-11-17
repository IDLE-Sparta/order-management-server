package com.sparta.ordermanagement.application.admin;

import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.exception.InvalidValueException;
import com.sparta.ordermanagement.application.output.user.UserOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserOutputPort userOutputPort;

    @Transactional
    public String signup(User user) {

        log.info("[AdminUserService]-[signup] Admin 회원가입 요청");

        //password 인코딩
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        //회원 중복 확인
        if(userOutputPort.existsByUserStringId(user.getUserStringId())){
            throw new InvalidValueException("동일한 Id를 가진 유저가 이미 존재합니다.");
        }

        return userOutputPort.saveUser(user, encodedPassword);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        log.info("[AdminUserService]-[getUsers]");
        return userOutputPort.findAll();
    }

    @Transactional
    public void updateUser(String userStringId, Role role) {

        log.info("[AdminUserService]-[updateUser]");

        Integer countOfUpdatedUser = userOutputPort.updateUserById(userStringId, role);

        if(countOfUpdatedUser == 0){
            throw new InvalidValueException("존재하지 않는 유저입니다.");
        }
    }

    @Transactional
    public void deleteUser(String userStringId) {

        log.info("[AdminUserService]-[deleteUser]");

        int deletedUserCount = userOutputPort.deleteUserByUserStringId(userStringId);

        if(deletedUserCount == 0){
            throw new InvalidValueException("존재하지 않는 유저입니다.");
        }
    }
}
