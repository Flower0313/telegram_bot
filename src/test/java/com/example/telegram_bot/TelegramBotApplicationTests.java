package com.example.telegram_bot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TelegramBotApplicationTests {

    @Test
    void contextLoads() throws IOException {
        // 大于等于2张图就走这串代码
        BufferedImage image1 = ImageIO.read(new File("/Users/xiaohua/Desktop/1.jpeg"));
        BufferedImage image2 = ImageIO.read(new File("/Users/xiaohua/Desktop/2.jpeg"));
        BufferedImage image3 = ImageIO.read(new File("/Users/xiaohua/Desktop/3.jpeg"));

        List<BufferedImage> imageList = new ArrayList<BufferedImage>();
        imageList.add(image1);
        imageList.add(image2);
        //imageList.add(image3);

        int index = 0;
        int maxWidth = 0;
        int maxHeight = 0;
        int rows = (int) Math.ceil((double) imageList.size() / 3);
        int columns = Math.min(imageList.size(), 3);

        for (BufferedImage image : imageList) {
            if (image.getWidth() > maxWidth) {
                maxWidth = image.getWidth();
            }
            if (image.getHeight() > maxHeight) {
                maxHeight = image.getHeight();
            }
        }
        System.out.println(rows + "||" + columns);
        BufferedImage mergedImage = new BufferedImage(maxWidth * columns, maxHeight * rows, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mergedImage.createGraphics();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (index < imageList.size()) {
                    g2d.drawImage(imageList.get(index++), i * maxWidth, j * maxHeight, null);
                }
            }
        }
        g2d.dispose();
        ImageIO.write(mergedImage, "png", new File("merged.png"));
    }

}
