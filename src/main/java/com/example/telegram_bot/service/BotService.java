package com.example.telegram_bot.service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.math.BigDecimal;

/**
 * @ClassName telegram_bot-BotService
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月22日10:56 - 周一
 * @Describe
 */
public interface BotService {
    void test();

    /**
     * 扣减余额 & 增加购买记录
     *
     * @param userId    用户id
     * @param phoenixId 凤id
     * @param subtract  余额
     */
    void subtractAndLink(Long userId, String phoenixId, BigDecimal subtract);
}
