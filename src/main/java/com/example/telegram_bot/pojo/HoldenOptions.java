package com.example.telegram_bot.pojo;

import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @ClassName wxproject-HoldenOptions
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年4月10日11:53 - 周一
 * @Describe
 */
public class HoldenOptions extends DefaultBotOptions {

    public HoldenOptions() {
        super.setProxyHost("127.0.0.1");
        super.setProxyPort(7890);
        super.setProxyType(ProxyType.SOCKS5);
    }
}
