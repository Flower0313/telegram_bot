package com.example.telegram_bot.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName telegram_bot-UserVO
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月23日14:30 - 周二
 * @Describe
 */
@Data
public class UserVO {
    /**
     * 用户主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 出击币余额
     */
    private BigDecimal balance;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 城市
     */
    private Integer cityId;

    /**
     * 用户类型代码
     */
    private Integer code;
}
