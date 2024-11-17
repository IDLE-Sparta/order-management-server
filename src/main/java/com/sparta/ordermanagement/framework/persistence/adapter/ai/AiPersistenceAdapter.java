package com.sparta.ordermanagement.framework.persistence.adapter.ai;

import com.sparta.ordermanagement.application.domain.ai.AiForRecommendProductName;
import com.sparta.ordermanagement.application.output.ai.AiOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.ai.AiEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import com.sparta.ordermanagement.framework.persistence.repository.ai.AiRepository;
import com.sparta.ordermanagement.framework.persistence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AiPersistenceAdapter implements AiOutputPort {
    private final UserRepository userRepository;
    private final AiRepository aiRepository;

    public void saveAfterUserSearch(AiForRecommendProductName domain, String userStringId) {
        UserEntity userEntity = userRepository.findByUserStringId(userStringId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(aiRepository.save(AiEntity.from(domain, userEntity)) == null) {
                throw new RuntimeException("AI wasn't saved");
        }
    }
}
