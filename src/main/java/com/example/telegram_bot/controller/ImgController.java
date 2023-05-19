package com.example.telegram_bot.controller;

import com.example.telegram_bot.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName telegram_bot-ImgController
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月19日14:53 - 周五
 * @Describe
 */
@RestController
public class ImgController {
    @Autowired
    private ImgService imgService;

    @GetMapping("/image/{pic}")
    public void getImg(HttpServletResponse resp,@PathVariable("pic") String pic) throws IOException {
        imgService.getPic(resp, pic);
    }
}
