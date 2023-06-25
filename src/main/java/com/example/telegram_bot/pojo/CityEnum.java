package com.example.telegram_bot.pojo;

import lombok.Getter;

/**
 * @ClassName telegram_bot-City
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月19日11:03 - 周五
 * @Describe
 */
@Getter
public enum CityEnum {
    /**
     * 城市枚举类
     */
    SH("上海", "sh"),
    SZ("深圳", "sz"),
    BJ("北京", "bj"),
    CS("长沙", "cs");

    private String key;
    private String value;

    CityEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {

        for (CityEnum item : CityEnum.values()) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }
}
