package com.example.telegram_bot.service.impl;

import com.example.telegram_bot.mapper.Bot;
import com.example.telegram_bot.pojo.Constant;
import com.example.telegram_bot.pojo.HoldenOptions;
import com.example.telegram_bot.pojo.Phoenix;
import com.example.telegram_bot.service.BotService;
import com.example.telegram_bot.service.ImgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @ClassName telegram_bot-BotServiceImpl
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月22日10:56 - 周一
 * @Describe
 */
@Service
@Slf4j
public class BotServiceImpl extends TelegramLongPollingBot implements BotService {
    @Autowired
    private Bot botMapper;

    /**
     * 加载telegram机器人
     */
    @PostConstruct
    private void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public BotServiceImpl() {
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
                    keyboardMarkup.setKeyboard(getInnerMenu(1, null));
                    // 创建一个 SendMessage 对象，并将 InlineKeyboardMarkup 对象作为参数传递给 setReplyMarkup() 方法
                    SendMessage message = SendMessage.builder()
                            .chatId(chatId)
                            .text("请选择以下感兴趣地区(数据每天都会同步更新):")
                            .replyMarkup(keyboardMarkup)
                            .build();
                    executeAsync(message);
                } else if (Constant.HELP.equals(messageText)) {
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    keyboardMarkup.setKeyboard(getInnerMenu(2, null));
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
            try {
                onCallbackQueryReceived(update.getCallbackQuery());
            } catch (Exception e) {
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

    public void onCallbackQueryReceived(CallbackQuery callbackQuery) throws TelegramApiException, IOException {
        // 处理内联键盘回调
        String data = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();

        //查询具体值
        if (data.contains(Constant.LIST)) {
            String id = data.split(Constant.SEPARATOR)[1].trim();

            Phoenix targetPhoenix = botMapper.getTargetPhoenix(id);

            Message preText = execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(id + "号技师准备中，请先去洗澡...")
                    .build());
            // 图片处理
            SendPhoto photo = SendPhoto.builder().chatId(chatId)
                    .photo(new InputFile(processImage(targetPhoenix)))
                    .caption(targetPhoenix.getRemark())
                    .build();
            execute(photo);
            //撤回提示消息
            execute(new DeleteMessage(String.valueOf(chatId), preText.getMessageId()));
        } else if (data.contains(Constant.PAGE)) {
            String choose = data.split(Constant.SEPARATOR)[1].trim();
            if (Constant.LAST.equals(choose)) {

            } else {

            }
        } else {
            List<Phoenix> phoenixes = botMapper.listPhoenix();
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(pinList(phoenixes))
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(getInnerMenu(3, phoenixes)).build())
                    .build());
        }
    }


    /**
     * 获取内嵌菜单
     *
     * @return 内嵌菜单
     */
    private List<List<InlineKeyboardButton>> getInnerMenu(int type, List<Phoenix> nums) {
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
        } else if (type == 2) {
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(InlineKeyboardButton.builder().text("购买积分").callbackData("pay").build());
            row1.add(InlineKeyboardButton.builder().text("积分查询").callbackData("balance").build());
            row1.add(InlineKeyboardButton.builder().text("使用说明").callbackData("book").build());
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row1.add(InlineKeyboardButton.builder().text("售后咨询").callbackData("sale").build());
            rows.add(row1);
            return rows;
        } else if (type == 3) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            // 将每个InlineKeyboardButton对象添加到row中
            for (int i = 0; i < nums.size(); i++) {
                Phoenix phoenix = nums.get(i);
                // 将每个菜单转换为一个InlineKeyboardButton对象
                row.add(InlineKeyboardButton.builder().text(String.valueOf(i + 1)).callbackData("list-" + phoenix.getId()).build());
                //一行菜单最多8列
                if (row.size() == 8) {
                    // 将row添加到rows中，并重新创建一个新的row
                    rows.add(row);
                    row = new ArrayList<>();
                }
            }

            if (!row.isEmpty()) {
                rows.add(row);
            }
            List<InlineKeyboardButton> page = new ArrayList<>();
            page.add(InlineKeyboardButton.builder().text("<<上一页").callbackData("page-last").build());
            page.add(InlineKeyboardButton.builder().text("下一页>>").callbackData("page-next").build());
            rows.add(page);
            return rows;
        } else {
            return rows;
        }
    }

    @Override
    public void test() {
        PageHelper.startPage(1, 10);
        List<Phoenix> phoenixes = botMapper.listPhoenix();
        PageInfo<Phoenix> info = new PageInfo<>(phoenixes);
        System.out.println(info + ">>>>");

    }

    /**
     * 展示凤列表
     *
     * @param phoenixes 凤列表实体
     * @return
     */
    private String pinList(List<Phoenix> phoenixes) {
        StringBuilder replyText = new StringBuilder();
        int i = 1;
        for (Phoenix phoenix : phoenixes) {
            String remark = phoenix.getRemark();
            replyText.append(i + 1).append(">>")
                    .append(remark.substring(0, Math.min(remark.length(), 30))).append("\n")
                    .append("最低消费:").append(phoenix.getMinPrice()).append("\n")
                    .append("--------------------------------------\n");
        }
        replyText.append("点击具体序号查看详情（含图片）↓");
        return replyText.toString();
    }


    /**
     * 将多张图片拼成一张
     */
    private File processImage(Phoenix phoenix) {
        try {
            File dir = new File("T:\\User\\Desktop\\公司\\telegram_bot\\src\\main\\resources\\imgs");
            String fileName = phoenix.getChannelId() + "-" + phoenix.getMessageId() + "-" + phoenix.getGroupId() + ".png";
            File afterFile = new File("T:\\User\\Desktop\\公司\\telegram_bot\\src\\main\\resources\\imgs\\" + fileName);
            if (afterFile.exists()) {
                return afterFile;
            } else {
                File[] files = dir.listFiles(pathname -> {
                    // 判断文件是否是图片文件
                    return pathname.isFile() && (
                            pathname.getName().endsWith(".jpg")
                    ) && pathname.getName().contains(phoenix.getGroupId());
                });
                if (files != null) {
                    int desiredWidth = 230;
                    int desiredHeight = 300;

                    List<BufferedImage> resizedImageList = new ArrayList<>();
                    IntStream.range(0, files.length).parallel().forEach(i -> {
                        try {
                            File file = files[i];
                            BufferedImage originalImage = ImageIO.read(file);
                            //全部统一大小
                            BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g2dResized = resizedImage.createGraphics();
                            g2dResized.drawImage(originalImage, 0, 0, desiredWidth, desiredHeight, null);
                            g2dResized.dispose();
                            resizedImageList.add(resizedImage);
                        } catch (IOException e) {
                            // 处理读取图像时的异常
                            e.printStackTrace();
                        }
                    });

                    int rows = (int) Math.ceil((double) resizedImageList.size() / 3);
                    int columns = Math.min(resizedImageList.size(), 3);
                    BufferedImage mergedImage = new BufferedImage(desiredWidth * columns, desiredHeight * rows, BufferedImage.TYPE_INT_RGB);

                    Graphics2D g2dMerged = mergedImage.createGraphics();
                    for (int i = 0; i < columns; i++) {
                        for (int j = 0; j < rows; j++) {
                            int index = i * rows + j;
                            if (index < resizedImageList.size()) {
                                BufferedImage resizedImage = resizedImageList.get(index);
                                g2dMerged.drawImage(resizedImage, i * desiredWidth, j * desiredHeight, null);
                                resizedImage.flush();
                            }
                        }
                    }
                    g2dMerged.dispose();
                    ImageIO.write(mergedImage, "png", afterFile);
                    //异步删除图片
                    CompletableFuture.runAsync(() -> {
                        for (File file : files) {
                            if (!file.delete()) {
                                log.info("文件删除失败");
                            }
                        }
                    });
                }
                return afterFile;
            }
        } catch (Exception e) {
            return new File("T:\\User\\Desktop\\公司\\telegram_bot\\src\\main\\resources\\imgs\\noimage.jpg");
        }
        //到时候以一张没图片的图片代替
    }
}
