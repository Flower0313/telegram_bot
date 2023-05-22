package com.example.telegram_bot.pojo;

import lombok.Data;

/**
 * @ClassName telegram_bot-Phoenix
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月22日10:26 - 周一
 * @Describe 凤实体表
 */
@Data
public class Phoenix {
    private Integer id;
    /**
     * 频道id
     */
    private String channelId;

    /**
     * 消息id
     */
    private Integer messageId;

    /**
     * 组id
     */
    private String groupId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String area;

    private boolean ifSw;
    private boolean ifBy;
    private boolean ifDoor;
    private boolean if96;

    /**
     * 展示内容
     */
    private String remark;

    /**
     * 最低价
     */
    private String minPrice;

    /**
     * 真实内容
     */
    private String realContent;

}
