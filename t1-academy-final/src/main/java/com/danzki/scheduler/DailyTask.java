package com.danzki.scheduler;

import com.danzki.dto.response.InfoResponseDto;
import com.danzki.exception.RestoreAllLimitsException;
import com.danzki.process.LimitProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyTask {
    private final LimitProcessor processor;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Moscow")
    public void restoreAllLimit() {
        try {
            log.info("Started scheduled task - restore dailyy limit to default value");
            InfoResponseDto dto = processor.restoreAllLimits();
            log.info("Scheduled task finished with message: " + dto.getMessage());
        } catch (Exception e) {
            log.info("Restore all daily limits failed.");
            throw new RestoreAllLimitsException("Restore all daily limits failed.");
        }
    }
}
