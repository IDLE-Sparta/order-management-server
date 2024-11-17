package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.service.AiService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.ai.AiRecommendProductNameRequest;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.InvalidAuthorizationException;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.RequestValidationException;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Tag(name = "사용자 - AI 질문 요청", description = "OWNER 이상의 권한 필요")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AiController {

    private final AiService aiService;

    @PostMapping("/ai")
    @ResponseStatus(HttpStatus.OK)
    public Map.Entry aiRecommendProductName(
            @RequestBody AiRecommendProductNameRequest aiRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {

        log.info("[AiController]-[aiRecommendProductName] API call");

        //Validation
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(bindingResult.getAllErrors().toString());
        }
        if(userDetails.getRole() != Role.OWNER){
            throw new InvalidAuthorizationException("API 접근 권한이 없습니다.");
        }

        String aiResponse = aiService.aiRecommendProductName(
                aiRequest.toDomain(userDetails.getUserStringId()),
                userDetails.getUserStringId());

        return Map.entry("responseContent", aiResponse);
    }
}
