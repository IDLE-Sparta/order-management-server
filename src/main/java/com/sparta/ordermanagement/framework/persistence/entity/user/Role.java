package com.sparta.ordermanagement.framework.persistence.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    CUSTOMER("고객"),
    OWNER("점주"),
    MANAGER("매니저"),
    MASTER("총괄 매니저");

    private final String value;

}
