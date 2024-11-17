package com.sparta.ordermanagement.application.service.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.ordermanagement.application.domain.ai.AiForRecommendProductName;
import com.sparta.ordermanagement.application.output.ai.AiOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AiService {

    private final AiOutputPort aiOutputPort;
    private RestTemplate restTemplate;

    public AiService (RestTemplateBuilder builder, AiOutputPort aiOutputPort) {
        this.restTemplate = builder.build();
        this.aiOutputPort = aiOutputPort;
    }
    @Value("${ai.key}")
    private String AI_KEY;

    private Map<String, Object> convertJsonToMap(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map ;

        try{
            // JSON을 Map으로 변환
            map = objectMapper.readValue(json, Map.class);
        }catch (Exception e){
            throw new RuntimeException("convert json to Map fail");
        }

        return map;
    }

    private String getTextFromResponse(Map<String, Object> map) {
        List<Map<String, Object>> candidates = (List<Map<String, Object>>) map.get("candidates");
        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
        List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
        String responseText = parts.get(0).get("text");
        return responseText;
    }

    private ResponseEntity<String> postHttpRequestToAiService(String requestContent){

        URI uri = UriComponentsBuilder
                .fromUriString("https://generativelanguage.googleapis.com")
                .path("/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", AI_KEY)
                .encode()
                .build()
                .toUri();

        log.info("uri = " + uri);

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 JSON 데이터 설정
        String jsonBody = String.format(
                "{ \"contents\": [ { \"parts\": [ { \"text\": \"%s\" } ] } ] }",
                requestContent+" 답변은 최대한 간결하게 50자 이하로"
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        return restTemplate.postForEntity(uri, requestEntity, String.class);
    }

    @Transactional
    public String aiRecommendProductName(AiForRecommendProductName domain, String userStringId){

        log.info("[AiService]-[aiRecommendProductName]");

        //Ai 서비스에 Request 날리기
        ResponseEntity<String> responseEntity = postHttpRequestToAiService(domain.getRequestContent());
        String responseJson = responseEntity.getBody();
        
        //Response 중 응답 Text 가져오기
        Map<String, Object> map = convertJsonToMap(responseJson);
        String responseText = getTextFromResponse(map);

        aiOutputPort.saveAfterUserSearch(AiForRecommendProductName.from(domain, responseText), userStringId);

        return responseText;
    }



}
