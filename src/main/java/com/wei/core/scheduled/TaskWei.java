package com.wei.core.scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@AllArgsConstructor
@Slf4j
public class TaskWei {

    /**
     * 秒 分 时 日 月 周
     */
    @Scheduled(cron = "0 */10 * * * *")
    public void wei() {
    }
}
