package com.example.telegram_bot.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName telegram_bot-ScheduledTask
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年6月01日15:41 - 周四
 * @Describe 定时任务
 */
@Component
@EnableAsync
public class ScheduledTask {

    /**
     * 并行定时任务，执行python爬取脚本
     */
    @Async
    @Scheduled(cron = "0/10 * * * * ?") //每10秒执行一次
    public void scheduledTaskByCorn() throws InterruptedException {

    }
}
