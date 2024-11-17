package com.sparta.ordermanagement.application.output.ai;

import com.sparta.ordermanagement.application.domain.ai.AiForRecommendProductName;
import com.sparta.ordermanagement.framework.persistence.entity.ai.AiEntity;

import java.util.Optional;

public interface AiOutputPort {

    void saveAfterUserSearch(AiForRecommendProductName domain, String userStringId);
}
