package com.example.telegram_bot.pojo;

import lombok.Getter;

/**
 * @ClassName telegram_bot-MenuEnum
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年6月05日17:27 - 周一
 * @Describe
 */
@Getter
public enum MenuEnum {
    CJ(10001, "/cj"),
    HELP(10002, "/help"),
    SIFT(10003, "/sift"),
    REGJSTER(10004, "/register");

    private Integer code;
    private String value;

    MenuEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static String getValueByKey(Integer code) {
        for (MenuEnum item : MenuEnum.values()) {
            if (item.code.equals(code)) {
                return item.value;
            }
        }
        return null;
    }
}
