package com.sparta.ordermanagement.framework.persistence.scheduler;

import com.sparta.ordermanagement.framework.persistence.repository.shop.ShopQueryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ShopScheduler {

    private static final int BASE_DAY_AGO = 1;
    private static final int BASE_HOUR = 3;

    private final ShopQueryRepository shopRepository;

    @Scheduled(cron = "0 0 3 * * ?") // 매일 오전 3시
    public void schedulingRatingStatistics() {
        log.info("별점 통합 스케줄 작업 시작: {}", LocalDateTime.now());

        LocalDateTime baseTime = LocalDateTime.now().minusDays(BASE_DAY_AGO).withHour(BASE_HOUR);
        shopRepository.updateShopRating(baseTime);

        log.info("별점 통합 스케줄 작업 완료: {}", LocalDateTime.now());
    }
}
