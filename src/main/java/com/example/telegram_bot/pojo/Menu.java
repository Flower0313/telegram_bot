package com.example.telegram_bot.pojo;

import lombok.Data;

/**
 * @ClassName telegram_bot-Menu
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年6月05日15:53 - 周一
 * @Describe
 */
@Data
public class Menu {
    private String menuCode;
    private Integer menuParent;
    private String menuValue;
    private String menuAdd;
    private String menuRemark;
}
