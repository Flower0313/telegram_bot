package com.example.telegram_bot.service.impl;

import com.example.telegram_bot.mapper.Bot;
import com.example.telegram_bot.pojo.Phoenix;
import com.example.telegram_bot.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

/**
 * @ClassName telegram_bot-ImgServiceImpl
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月19日14:55 - 周五
 * @Describe
 */
@Service
public class ImgServiceImpl implements ImgService {
    @Autowired
    private Bot botMapper;

    @Override
    public void getPic(HttpServletResponse resp, String pic) throws IOException {
        //获取图片的url名称
        File imageFile = new File("/opt/module/mars.jpg");
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        // 将图像内容写入HTTP响应流
        resp.setContentType("image/png");
        resp.getOutputStream().write(imageBytes);
    }

    @Override
    public void test() {
        List<Phoenix> phoenixes = botMapper.listPhoenix(null);
        System.out.println(phoenixes);
    }
}
