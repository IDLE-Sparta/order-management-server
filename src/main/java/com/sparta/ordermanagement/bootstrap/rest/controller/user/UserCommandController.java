package com.sparta.ordermanagement.bootstrap.rest.controller.user;

import com.sparta.ordermanagement.application.service.user.UserService;
import com.sparta.ordermanagement.bootstrap.rest.dto.user.UserSigninRequest;
import com.sparta.ordermanagement.bootstrap.rest.dto.user.UserSignupRequest;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.RequestValidationException;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "사용자 - 유저 관리", description = "권한 불필요")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserCommandController {

    private final UserService userService;

    private static void requestValidation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String bindingErrorMessage = bindingResult.getAllErrors().toString();
            throw new RequestValidationException(bindingErrorMessage);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Map.Entry signupUser(@RequestBody @Valid UserSignupRequest userSignupRequest, BindingResult bindingResult) {

        log.info("[UserCommandController]-[signupUser] API call");

        //Validation
        requestValidation(bindingResult);
        if(userSignupRequest.getRole().equals(Role.MANAGER)|| userSignupRequest.getRole().equals(Role.MASTER)){
            throw new RequestValidationException("관리자 계정은 등록할 수 없습니다.");
        }

        String signupUserStringId = userService.signup(userSignupRequest.toDomain());

        return Map.entry("userStringId", signupUserStringId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signin")
    public ResponseEntity<String> signinUser(@RequestBody @Valid UserSigninRequest userSigninRequest, BindingResult bindingResult){

        log.info("[UserCommandController]-[signinUser] API call");

        //Validation
        requestValidation(bindingResult);

        String accessToken = userService.signin(userSigninRequest.toDomain());

        // 응답 헤더에 토큰 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body("user signin successfully");
    }

}
