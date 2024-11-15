package com.sparta.ordermanagement.application.service;


import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.domain.user.UserForSignin;
import com.sparta.ordermanagement.application.domain.user.UserForSignup;
import com.sparta.ordermanagement.application.exception.InvalidValueException;
import com.sparta.ordermanagement.application.exception.shop.ShopIdInvalidException;
import com.sparta.ordermanagement.application.exception.user.UserAccessDeniedException;
import com.sparta.ordermanagement.application.output.UserOutputPort;
import com.sparta.ordermanagement.bootstrap.util.JwtUtil;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserOutputPort userOutputPort;
    private final JwtUtil jwtUtil;

    @Transactional
    public String signup(User user){

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        //회원 중복 확인
        if(userOutputPort.existsByUserStringId(user.getUserStringId())){
            throw new InvalidValueException("동일한 Id를 가진 유저가 이미 존재합니다.");
        }

        //회원 권한 확인
        if(user.getRole().equals(Role.MANAGER)|| user.getRole().equals(Role.MASTER)){
            throw new InvalidValueException("관리자 계정은 등록할 수 없습니다.");
        }

        return userOutputPort.saveUser(user, encodedPassword);
    }


    public String signin(UserForSignin userRequest) {

        log.info("[UserService]-[signin] 로그인 요청");

        //회원 아이디 확인
        User findUser = userOutputPort.findByUserStringId(userRequest.userStringId())
                .orElseThrow(() -> new InvalidValueException("ID가 잘못 되었습니다."));

        //비밀번호 확인
        if(!passwordEncoder.matches(userRequest.password(), findUser.getPassword())){
            log.info("[UserService]-[signin] 유효하지 않은 비밀번호");
            throw new InvalidValueException("유효하지 않은 비밀번호");
        }

        String token = jwtUtil.createToken(findUser.getUserStringId(), findUser.getRole());
        log.info("[UserService]-[signin] 토큰 생성 성공");

        return token;
    }

    public User findByUserStringId(String userId) {
        return userOutputPort.findByUserStringId(userId)
            .orElseThrow(() -> new InvalidValueException("Id에 해당하는 유저가 존재하지 않습니다. Id=" + userId));
    }

    public void validateOwnerRole(Role role) {
        if (!role.equals(Role.OWNER)) {
            throw new UserAccessDeniedException(role);
        }
    }
}
