package com.example.telegram_bot.controller;

import com.example.telegram_bot.pojo.Constant;
import com.example.telegram_bot.pojo.HoldenOptions;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
public class BotController extends TelegramLongPollingBot {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public BotController() {
        super(new HoldenOptions(), Constant.TOKEN);
    }

    /**
     * @return bot_api创建者id
     */
    public int creatorId() {
        return 313;
    }

    /**
     * 处理方法
     *
     * @param update 用户消息
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && !update.hasCallbackQuery()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            // 创建一个 InlineKeyboardMarkup 对象，并设置键盘的按钮
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rows = new ArrayList<>();
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().setText("Option 1").setCallbackData("option1"));
            row1.add(new InlineKeyboardButton().setText("Option 2").setCallbackData("option2"));
            rows.add(row1);
            keyboardMarkup.setKeyboard(rows);

            // 创建一个 SendMessage 对象，并将 InlineKeyboardMarkup 对象作为参数传递给 setReplyMarkup() 方法
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text("内嵌菜单")
                    .replyMarkup(keyboardMarkup)
                    .build();

            // 发送消息
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            try {
                sendTextRecall(update);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return 机器人用户名
     */
    @Override
    public String getBotUsername() {
        return Constant.BOT_USERNAME;
    }

    /**
     * 简单发送给消息
     *
     * @param message 消息
     */
    private void sendText(SendMessage message) throws TelegramApiException {
        executeAsync(message);
    }

    /**
     * 带撤回的消息发送
     *
     * @param update 用户消息
     */
    private void sendTextRecall(Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        Message message = execute(SendMessage.builder()
                .chatId(chatId)
                .text(update.getMessage().getText())
                .build());
        //5秒后删除消息
        executor.schedule(() -> {
            DeleteMessage deleteMessage = new DeleteMessage(chatId.toString(), message.getMessageId());
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }, Constant.DELAY_SECONDS, TimeUnit.SECONDS);
        executor.shutdown();
    }

//    private void keyBoardMenu(Long chatId) {
//        // 创建一个新的ReplyKeyboardMarkup
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//
//        // 创建一个新的行
//        List<KeyboardRow> keyboard = new ArrayList<>();
//
//        // 创建第一行按钮
//        KeyboardRow row1 = new KeyboardRow();
//        row1.add("Button 1");
//        row1.add("Button 2");
//
//        // 创建第二行按钮
//        KeyboardRow row2 = new KeyboardRow();
//        row2.add("Button 3");
//        row2.add("Button 4");
//
//        // 将两行按钮添加到键盘中
//        keyboard.add(row1);
//        keyboard.add(row2);
//
//        // 设置键盘
//        keyboardMarkup.setKeyboard(keyboard);
//
//        // 将键盘添加到消息中
//        SendMessage message = SendMessage.builder()
//                .chatId(chatId)
//                .text("This is an example message with a menu.")
//                .replyMarkup(keyboardMarkup)
//                .build();
//
//
//        // 发送消息
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    public void onCallbackQueryReceived(CallbackQuery callbackQuery) {
        // 处理内联键盘回调
        String data = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();

        // 创建一个 SendMessage 对象，并设置消息的目标聊天ID和文本内容
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("针对菜单回复：" + data)
                .build();

        // 发送消息
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
