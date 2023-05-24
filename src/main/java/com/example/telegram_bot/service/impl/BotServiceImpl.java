package com.example.telegram_bot.service.impl;

import com.example.telegram_bot.mapper.Bot;
import com.example.telegram_bot.pojo.Constant;
import com.example.telegram_bot.pojo.HoldenOptions;
import com.example.telegram_bot.pojo.Phoenix;
import com.example.telegram_bot.pojo.UserVO;
import com.example.telegram_bot.redis.RedisDao;
import com.example.telegram_bot.service.BotService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
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
import java.math.BigDecimal;
import java.util.*;
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

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private RedisDao redisDao;

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


    ScheduledExecutorService executor = Executors.newScheduledThreadPool(20);

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
        try {
            Message message = update.getMessage();
//            if (Objects.nonNull(message) && (Constant.PRIVATE).equals(message.getChat().getType())) {
            /*
             * 和机器人交互
             * */
            if (update.hasMessage() && message.hasText() && !update.hasCallbackQuery()) {
                try {
                    long chatId = message.getChatId();
                    String messageText = message.getText();
                    if (Constant.CJ.equals(messageText)) {
                        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                        // 创建一个 InlineKeyboardMarkup 对象，并设置键盘的按钮
                        keyboardMarkup.setKeyboard(getInnerMenu(Constant.PHOENIX_MENU, null, "", ""));
                        // 创建一个 SendMessage 对象，并将 InlineKeyboardMarkup 对象作为参数传递给 setReplyMarkup() 方法
                        executeAsync(SendMessage.builder()
                                .chatId(chatId)
                                .text("请选择以下感兴趣地区(数据每天00:00开始更新最新数据):\n由于是个人运营，所以服务器费用也是自己出，象征性收点小钱;" +
                                        "相比那些动辄单个30元的楼凤，我这边算是讨口饭吃了，而且数据量还很全。")
                                .replyMarkup(keyboardMarkup)
                                .build());
                    } else if (Constant.HELP.equals(messageText)) {
                        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                        keyboardMarkup.setKeyboard(getInnerMenu(Constant.USER_MENU, null, "", ""));
                        executeAsync(SendMessage.builder()
                                .chatId(chatId)
                                .text("帮助列表:")
                                .replyMarkup(keyboardMarkup)
                                .build());
                    } else if (Constant.REGISTER.equals(messageText)) {
                        Long userId = message.getFrom().getId();
                        if (Objects.isNull(botMapper.selectUser(userId))) {
                            botMapper.addUser(message.getFrom().getId());
                            sendTextRecall(update, "恭喜注册成功！送你10枚【CJ币】");
                        } else {
                            sendTextRecall(update, "你已经是本凤皇帮会员,请洗澡去~");
                        }
                    } else {
                        executeAsync(SendMessage.builder()
                                .chatId(chatId)
                                .text("输入 /cj 开始选凤\n输入 /help 获取其他功能\n输入 /register 点击注册送【出击币】").build());
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
            /*} else if (Objects.nonNull(message) && (Constant.SUPERGROUP).equals(message.getChat().getType())) {
             *//*
             * 和群组聊天
             * *//*

                executeAsync(SendMessage.builder()
                        .chatId(message.getChatId())
                        .text("过来单独找我私聊呀！！")
                        .build());
            }
            *//*
             * 和频道聊天
             * *//*
            else {
                Message channelPost = update.getChannelPost();
                executeAsync(SendMessage.builder()
                        .chatId(channelPost.getChatId())
                        .text("你好，这是凤皇帮频道" + channelPost.getText())
                        .build());

            }*/
        } catch (Exception e) {
            e.printStackTrace();
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
     * @param chatId 对话ID
     * @param text   文本
     */
    private void sendText(Long chatId, String text) throws TelegramApiException {
        executeAsync(SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build());
    }



    /**
     * 带撤回的消息发送
     *
     * @param update 用户消息
     */
    private void sendTextRecall(Update update, String text) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        Message message = execute(SendMessage.builder()
                .chatId(chatId)
                .text(text)
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
    }

    public void onCallbackQueryReceived(CallbackQuery callbackQuery) {
        try {
            // 处理内联键盘回调
            String data = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            Integer messageId = callbackQuery.getMessage().getMessageId();
            Long userId = callbackQuery.getFrom().getId();

            //查询具体值
            if (data.contains(Constant.LIST)) {
                showSinglePhoenix(chatId, userId, data.split(Constant.SEPARATOR)[1].trim());
            } else if (data.contains(Constant.PAGE)) {
                PageInfo<Phoenix> phoenixes = PageHelper.startPage(Integer.parseInt(data.split(Constant.SEPARATOR)[1].trim()), Constant.PAGE_SIZE).doSelectPageInfo(() -> botMapper.listPhoenix());
                //直接在原有消息基础上修改内容
                execute(EditMessageText.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .text(pinList(phoenixes.getList()))
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(getInnerMenu(Constant.PHOENIX_LIST, phoenixes, "", "")).build())
                        .build());
            } else if (data.contains(Constant.BALANCE)) {
                UserVO userVO = botMapper.selectUser(userId);
                if (Objects.isNull(userVO)) {
                    this.sendText(chatId, "您的身份为：未注册\n您的CJ币余额为：0\n请输入 /register 进行注册");
                } else {
                    this.sendText(chatId, "您的身份为：" + userVO.getType() + "\n您的CJ币余额为：" + userVO.getBalance());
                }
            } else if (data.contains(Constant.PAY)) {
                chooseMethod(chatId, userId);
            } else if (data.contains(Constant.BOOK)) {
                this.sendText(chatId, "（1）注册只能输入 /register 来执行，用户分为三种：\n" +
                        "\t第一种：凤斗者【免费】，只能使用CJ币来解锁所有楼凤；\n" +
                        "\t第二种：凤斗王【30元】，解锁任一地区的楼凤；比如你在上海，你能免CJ币查询上海所有楼凤，而其他地区需要使用CJ币购买；\n" +
                        "\t第三种：凤号斗罗【99元】，解锁全国楼凤；\n" +
                        "（2）凡是新注册用户，初始CJ币都会奖励10枚；\n" +
                        "（3）目前定价是【7枚/凤】，购买定价为【1元/5枚】，后期会根据楼凤的质量来区分不同的定价，但能保证不贵；且一次兑换永久有效，比如你解锁了一个楼凤，此后这个楼凤对你永久免费展示；\n" +
                        "（4）套餐分为以下三种：\n" +
                        "\t探花郎：【6元/35枚】\n" +
                        "\t探花秀才：【15元/95枚】\n" +
                        "\t淫王：【25元/160枚】");
            } else if (data.contains(Constant.BUY)) {
                buyPhoenix(chatId, userId, data.split(Constant.SEPARATOR));
            } else if (data.contains(Constant.DELAY_METHOD)) {
                executeAsync(SendPhoto.builder().chatId(chatId)
                        .photo(new InputFile(new File("T:\\User\\Desktop\\公司\\telegram_bot\\src\\main\\resources\\imgs\\method1.jpg")))
                        .caption("千万别忘记备注您的用户编号：" + userId)
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(getInnerMenu(Constant.RED_BAG, null, "", "")).build())
                        .build());
            } else if (data.equals(String.valueOf(Constant.RED_BAG))) {
                this.sendText(chatId, "已进入24小时验证通道，24小时内未成功原路退回");
            } else {
                //分页
                PageInfo<Phoenix> phoenixes = PageHelper.startPage(Constant.ONE, Constant.PAGE_SIZE)
                        .doSelectPageInfo(() -> botMapper.listPhoenix());
                execute(SendMessage.builder()
                        .chatId(chatId)
                        .text(pinList(phoenixes.getList()))
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(getInnerMenu(Constant.PHOENIX_LIST, phoenixes, "", "")).build())
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查看单个凤详细信息
     *
     * @param chatId 频道id
     * @param userId 用户id
     * @param id     凤id
     */
    private void showSinglePhoenix(Long chatId, Long userId, String id) throws TelegramApiException {
        Phoenix targetPhoenix = botMapper.getTargetPhoenix(id);
        Message preText = execute(SendMessage.builder()
                .chatId(chatId)
                .text(id + "号凤女准备中，请先去洗澡...")
                .build());
        Integer ifLocked = botMapper.userPhoenixAction(userId, id);
        if (Objects.isNull(ifLocked) || ifLocked == 0) {
            //生成UUID，用于后期更改词条消息的隐藏内容
            String uuid = UUID.randomUUID().toString().replaceAll(Constant.SEPARATOR, Constant.VOID);
            // 图片处理
            SendPhoto photo = SendPhoto.builder().chatId(chatId)
                    .photo(new InputFile(processImage(targetPhoenix)))
                    .caption(targetPhoenix.getRemark())
                    .replyMarkup(InlineKeyboardMarkup.builder()
                            .keyboard(getInnerMenu(Constant.SINGLE_PHOENIX, null, id, uuid)).build())
                    .build();
            Message execute = execute(photo);
            //将UUID存入redis，方便后续能取到其值
            redisDao.setSecond(uuid, execute.getMessageId(), Constant.THIRTY);
        } else {
            //已解锁，直接展示真实内容
            executeAsync(SendPhoto.builder().chatId(chatId)
                    .photo(new InputFile(processImage(targetPhoenix)))
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(getInnerMenu(Constant.AFTER_BUY, null, "", "")).build())
                    .caption(targetPhoenix.getRealContent()).build()
            );
        }
        //撤回提示消息
        executeAsync(new DeleteMessage(String.valueOf(chatId), preText.getMessageId()));
    }

    /**
     * 检查是不是凤皇帮用户
     *
     * @param userId 用户id
     * @return 是否存在
     */
    private UserVO checkIsUser(Long userId) {
        UserVO userVO = botMapper.selectUser(userId);
        if (Objects.nonNull(userVO)) {
            return userVO;
        }
        return null;
    }

    /**
     * 兑换凤
     *
     * @param chatId 频道id
     * @param userId 用户id
     * @param ids    获取redis的value
     */
    private void buyPhoenix(Long chatId, Long userId, String[] ids) throws TelegramApiException {
        UserVO userVO = botMapper.selectUser(userId);
        if (Objects.nonNull(userVO)) {
            BigDecimal subtract = userVO.getBalance().subtract(new BigDecimal("7"));
            if (subtract.compareTo(BigDecimal.ZERO) >= 0) {
                String phoenixId = ids[1].trim();
                //更改用户余额
                CompletableFuture.runAsync(() -> {
                    subtractAndLink(userId, phoenixId, subtract, ids, chatId);
                });
            } else {
                this.sendText(chatId, "CJ币不足，请点击充值按钮！");
            }
        } else {
            this.sendText(chatId, "请先点击 /register 成为凤皇帮用户!");
        }
    }


    /**
     * 选择付款方式
     */
    private void chooseMethod(Long chatId, Long userId) throws TelegramApiException {
        executeAsync(SendMessage.builder()
                .chatId(chatId)
                .text("您的用户编号为：【" + userId + "】\n" +
                        "购买方式：\n" +
                        "（1）积分延时到账：\n" +
                        "● 发送支付宝口令红包【必须备注您的用户编号】，没有备注用户编号的，钱会在24小时退回到您的账户下；\n" +
                        "● 若24小时内没处理，系统不会收你的红包，并且还会赠予你【20】枚CJ币以表歉意；\n" +
                        "（2）积分实时到账：\n" +
                        "● 实时到账，可以到帐后立马解锁；\n" +
                        "点击下面按钮，查看具体的付款方式及其步骤：")
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(getInnerMenu(Constant.PAY_METHOD, null, "", "")).build())
                .build()
        );
    }

    /**
     * 获取内嵌菜单
     *
     * @return 内嵌菜单
     */
    private <T> List<List<InlineKeyboardButton>> getInnerMenu(int type, PageInfo<T> pageInfo, String id, String redisId) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        switch (type) {
            case Constant.PHOENIX_MENU: {
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(InlineKeyboardButton.builder().text("上海").callbackData(Constant.SH).build());
                //row1.add(InlineKeyboardButton.builder().text("深圳(开发中)").callbackData("sz").build());
                //row1.add(InlineKeyboardButton.builder().text("北京(开发中)").callbackData("bj").build());
                //List<InlineKeyboardButton> row2 = new ArrayList<>();
                //row2.add(InlineKeyboardButton.builder().text("广州(开发中)").callbackData("gz").build());
                //row2.add(InlineKeyboardButton.builder().text("长沙(开发中)").callbackData("cs").build());
                rows.add(row1);
                //rows.add(row2);
                return rows;
            }
            case Constant.USER_MENU:
                List<InlineKeyboardButton> row3 = new ArrayList<>();
                row3.add(InlineKeyboardButton.builder().text("购买积分").callbackData(Constant.PAY).build());
                row3.add(InlineKeyboardButton.builder().text("用户信息").callbackData(Constant.BALANCE).build());
                row3.add(InlineKeyboardButton.builder().text("使用说明").callbackData(Constant.BOOK).build());
                row3.add(InlineKeyboardButton.builder().text("售后反馈").callbackData(Constant.SALE).build());
                List<InlineKeyboardButton> row31 = new ArrayList<>();
                row31.add(InlineKeyboardButton.builder().text("兑换身份").callbackData(Constant.IDENTITY).build());
                rows.add(row3);
                rows.add(row31);
                return rows;
            case Constant.PHOENIX_LIST:
                List<T> list = pageInfo.getList();
                List<InlineKeyboardButton> row = new ArrayList<>();
                // 将每个InlineKeyboardButton对象添加到row中
                for (T t : list) {
                    Phoenix phoenix = (Phoenix) t;
                    // 将每个菜单转换为一个InlineKeyboardButton对象
                    row.add(InlineKeyboardButton.builder().text(String.valueOf(phoenix.getId())).callbackData("list-" + phoenix.getId()).build());
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
                if (pageInfo.isHasPreviousPage()) {
                    page.add(InlineKeyboardButton.builder().text("首页").callbackData("page-" + Constant.ONE).build());
                    page.add(InlineKeyboardButton.builder().text("<<上一页").callbackData("page-" + pageInfo.getPrePage()).build());
                }
                if (pageInfo.isHasNextPage()) {
                    page.add(InlineKeyboardButton.builder().text("下一页>>").callbackData("page-" + pageInfo.getNextPage()).build());
                    page.add(InlineKeyboardButton.builder().text("尾页").callbackData("page-" + pageInfo.getPages()).build());
                }
                rows.add(page);
                return rows;
            case Constant.SINGLE_PHOENIX:
                List<InlineKeyboardButton> row4 = new ArrayList<>();
                row4.add(InlineKeyboardButton.builder().text("【7】枚").callbackData("buy-" + id + "-" + redisId).build());
                row4.add(InlineKeyboardButton.builder().text("充值").callbackData(Constant.PAY).build());
                row4.add(InlineKeyboardButton.builder().text("余额查询").callbackData(Constant.BALANCE).build());
                rows.add(row4);
                return rows;
            case Constant.AFTER_BUY:
                List<InlineKeyboardButton> row5 = new ArrayList<>();
                row5.add(InlineKeyboardButton.builder().text("充值").callbackData(Constant.PAY).build());
                row5.add(InlineKeyboardButton.builder().text("余额查询").callbackData(Constant.BALANCE).build());
                rows.add(row5);
                return rows;
            case Constant.PAY_METHOD:
                List<InlineKeyboardButton> row6 = new ArrayList<>();
                row6.add(InlineKeyboardButton.builder().text("延时到账").callbackData(Constant.DELAY_METHOD).build());
                row6.add(InlineKeyboardButton.builder().text("实时到账").callbackData(Constant.TIMELY_METHOD).build());
                rows.add(row6);
                return rows;
            case Constant.RED_BAG:
                List<InlineKeyboardButton> row7 = new ArrayList<>();
                row7.add(InlineKeyboardButton.builder().text("红包已发送,点击验证").callbackData(String.valueOf(Constant.RED_BAG)).build());
                rows.add(row7);
                return rows;
            default:
                return rows;
        }
    }

    @Override
    public void test() {
        PageInfo<Phoenix> objectPageInfo = PageHelper.startPage(3, 10).doSelectPageInfo(() -> botMapper.listPhoenix());
        System.out.println(objectPageInfo);
    }

    @Override
    @Transactional
    public void subtractAndLink(Long userId, String phoenixId, BigDecimal subtract, String[] ids, Long chatId) {
        // 开始事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //更新余额
            botMapper.updateBalance(userId, subtract);
            //新增活动
            botMapper.linkBuyAction(userId, phoenixId, Constant.UNLOCKED);
            //从redis中获取已经显示的消息，将它替换展开
            executeAsync(EditMessageCaption.builder()
                    .chatId(chatId)
                    .messageId((Integer) redisDao.get(ids[2].trim()))
                    .caption(botMapper.realContent(phoenixId))
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(getInnerMenu(Constant.AFTER_BUY, null, "", "")).build())
                    .build());
            // 提交事务
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("事务报错：" + e);
            // 回滚事务
            transactionManager.rollback(status);
        }
    }

    /**
     * 展示凤列表
     *
     * @param phoenixes 凤列表实体
     * @return
     */
    private String pinList(List<Phoenix> phoenixes) {
        StringBuilder replyText = new StringBuilder();
        for (Phoenix phoenix : phoenixes) {
            String remark = phoenix.getRemark();
            replyText.append(phoenix.getId()).append(">>")
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
