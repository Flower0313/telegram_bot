package com.example.telegram_bot.service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName telegram_bot-ImgService
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月19日14:54 - 周五
 * @Describe
 */

public interface ImgService{
    void getPic(HttpServletResponse resp, String pic) throws IOException;

    void test();
}
