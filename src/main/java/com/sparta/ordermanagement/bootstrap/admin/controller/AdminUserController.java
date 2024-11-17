package com.sparta.ordermanagement.bootstrap.admin.controller;

import com.sparta.ordermanagement.application.admin.AdminUserService;
import com.sparta.ordermanagement.application.exception.InvalidValueException;
import com.sparta.ordermanagement.bootstrap.admin.dto.UserAdminSignupRequest;
import com.sparta.ordermanagement.bootstrap.admin.dto.UserGetResponse;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.ForbiddenActionException;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.RequestValidationException;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "관리자 - 유저 관리", description = "MANAGER 이상의 권한 필요")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/v1/users")
@Slf4j
public class AdminUserController {

    private final AdminUserService adminUserService;

    private static void validateUserRoleForAccess(UserDetailsImpl userDetails) {
        if(!(userDetails.getRole() == Role.MANAGER || userDetails.getRole() == Role.MASTER)) {
            throw new ForbiddenActionException("API 접근 권한이 없습니다.");
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Map.Entry createAdmin(
            @RequestBody @Valid UserAdminSignupRequest userAdminSignupRequest, BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        log.info("[AdminUserController]-[createAdmin] API call");

        // Validation
        if (bindingResult.hasErrors()) {
            String bindingErrorMessage = bindingResult.getAllErrors().toString();
            throw new RequestValidationException(bindingErrorMessage);
        }
        if(!(userDetails.getRole() == Role.MANAGER || userDetails.getRole() == Role.MASTER || userDetails.getRole() == Role.OWNER)) {
            //이후 OWNER 삭제하기
            throw new ForbiddenActionException("API 접근 권한이 없습니다.");
        }
        if(!(userAdminSignupRequest.role().equals(Role.MANAGER.name())|| userAdminSignupRequest.role().equals(Role.MASTER.name()))){
            throw new InvalidValueException("일반 계정 등록은 다른 API 를 사용해야 합니다.");
        }

        String signupUserStringId = adminUserService.signup(userAdminSignupRequest.toDomain());

        return Map.entry("userStringId", signupUserStringId);
    }


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserGetResponse> getUsers(@AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info("[AdminUserController]-[getUsers] API call");

        // Validation
        validateUserRoleForAccess(userDetails);

        return adminUserService.getUsers().stream()
                .map(UserGetResponse::from)
                .toList();
    }

    @PatchMapping("/{user_string_id}/{role}")
    @ResponseStatus(HttpStatus.OK)
    public Map.Entry updateUser(
            @PathVariable("user_string_id") String userStringId,
            @PathVariable("role") Role role,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info("[AdminUserController]-[updateUser] API call");

        // Validation
        validateUserRoleForAccess(userDetails);

        adminUserService.updateUser(userStringId, role);

        return Map.entry("userStringId", userStringId);
    }

    @DeleteMapping("/{user_string_id}")
    @ResponseStatus(HttpStatus.OK)
    public Map.Entry deleteUser(
            @PathVariable("user_string_id") String userStringId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info("[AdminUserController]-[deleteUser] API call");

        // Validation
        validateUserRoleForAccess(userDetails);

        adminUserService.deleteUser(userStringId);

        return Map.entry("userStringId", userStringId);
    }

}
