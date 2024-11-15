package com.sparta.ordermanagement.application.exception.user;

import static com.sparta.ordermanagement.application.exception.message.UserErrorMessage.USER_ACCESS_DENIED;

import com.sparta.ordermanagement.application.exception.ForbiddenException;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;

public class UserAccessDeniedException extends ForbiddenException {

    public UserAccessDeniedException(Role userRole) {
        super(String.format(USER_ACCESS_DENIED.getMessage(), userRole));
    }
}
