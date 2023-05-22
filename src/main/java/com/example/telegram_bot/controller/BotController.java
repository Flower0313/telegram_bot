package com.example.telegram_bot.controller;

import com.example.telegram_bot.mapper.Bot;
import com.example.telegram_bot.pojo.CityEnum;
import com.example.telegram_bot.pojo.Constant;
import com.example.telegram_bot.pojo.HoldenOptions;
import com.example.telegram_bot.pojo.Phoenix;
import com.example.telegram_bot.service.BotService;
import com.example.telegram_bot.service.ImgService;
import com.example.telegram_bot.service.impl.BotServiceImpl;
import com.example.telegram_bot.service.impl.ImgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName telegram_bot-BotController
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月18日16:40 - 周四
 * @Describe
 */
@RestController
public class BotController {
    @Autowired
    private BotService botService;

    @GetMapping("/test")
    public void test(){
        botService.test();
    }
}
