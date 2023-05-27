package com.example.telegram_bot.service;

import com.example.telegram_bot.pojo.UserVO;
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
     * 扣减余额 & 增加购买记录 - 事务处理
     *
     * @param userId    用户id
     * @param phoenixId 凤id
     * @param subtract  余额
     * @param ids       其他id
     * @param chatId    频道id
     */
    void subtractAndLink(Long userId, String phoenixId, BigDecimal subtract, String[] ids, Long chatId);

    /**
     * 检查用户
     *
     * @return
     */
    UserVO checkUser();
}
