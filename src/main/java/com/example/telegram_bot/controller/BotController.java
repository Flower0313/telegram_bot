package com.example.telegram_bot.controller;

import com.example.telegram_bot.pojo.CityEnum;
import com.example.telegram_bot.pojo.Constant;
import com.example.telegram_bot.pojo.HoldenOptions;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
            try {
                long chatId = update.getMessage().getChatId();
                String messageText = update.getMessage().getText();
                if (Constant.CJ.equals(messageText)) {
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    // 创建一个 InlineKeyboardMarkup 对象，并设置键盘的按钮
                    keyboardMarkup.setKeyboard(getInnerMenu(1));
                    // 创建一个 SendMessage 对象，并将 InlineKeyboardMarkup 对象作为参数传递给 setReplyMarkup() 方法
                    SendMessage message = SendMessage.builder()
                            .chatId(chatId)
                            .text("请选择以下感兴趣地区(数据每天都会同步更新):")
                            .replyMarkup(keyboardMarkup)
                            .build();
                    executeAsync(message);
                } else if (Constant.HELP.equals(messageText)) {
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    keyboardMarkup.setKeyboard(getInnerMenu(2));
                    SendMessage message = SendMessage.builder()
                            .chatId(chatId)
                            .text("帮助列表:")
                            .replyMarkup(keyboardMarkup)
                            .build();
                    executeAsync(message);
                } else {
                    executeAsync(SendMessage.builder()
                            .chatId(chatId)
                            .text("输入 /cj 开始选凤\n输入 /help 获取帮助").build());
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            onCallbackQueryReceived(update.getCallbackQuery());
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

    public void onCallbackQueryReceived(CallbackQuery callbackQuery) {
        // 处理内联键盘回调
        String data = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();

        //传多张图片
        List<InputMedia> mediaList = new ArrayList<>();
        //必须是公网链接
        mediaList.add(InputMediaPhoto.builder().media("http://43.142.117.50:55551/image/1.jpg").caption("介绍").build());
        mediaList.add(InputMediaPhoto.builder().media("http://43.142.117.50:55551/image/1.jpg").build());
        mediaList.add(InputMediaPhoto.builder().media("http://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png").build());
        SendMediaGroup mediaGroup = new SendMediaGroup();
        mediaGroup.setChatId(chatId);
        mediaGroup.setMedias(mediaList);



        /*SendPhoto photo = SendPhoto.builder().chatId(chatId)
                .photo(new InputFile(new File("T:\\User\\Desktop\\公司\\telegram_bot\\src\\main\\resources\\imgs\\mars.jpg")))
                .caption("上海老师-小结-包多少-闵行区")
                .build();*/
        // 发送消息
        try {
            System.out.println("进来了");
            execute(mediaGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取内嵌菜单
     *
     * @return 内嵌菜单
     */
    private List<List<InlineKeyboardButton>> getInnerMenu(int type) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        if (type == 1) {
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(InlineKeyboardButton.builder().text("上海").callbackData("sh").build());
            row1.add(InlineKeyboardButton.builder().text("深圳(开发中)").callbackData("sz").build());
            row1.add(InlineKeyboardButton.builder().text("北京(开发中)").callbackData("bj").build());
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(InlineKeyboardButton.builder().text("广州(开发中)").callbackData("gz").build());
            row2.add(InlineKeyboardButton.builder().text("长沙(开发中)").callbackData("cs").build());
            rows.add(row1);
            rows.add(row2);
            return rows;
        } else {
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(InlineKeyboardButton.builder().text("购买积分").callbackData("pay").build());
            row1.add(InlineKeyboardButton.builder().text("积分查询").callbackData("balance").build());
            row1.add(InlineKeyboardButton.builder().text("使用说明").callbackData("book").build());
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row1.add(InlineKeyboardButton.builder().text("售后咨询").callbackData("sale").build());
            rows.add(row1);
            return rows;
        }
    }

}
