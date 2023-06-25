package com.example.telegram_bot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

//@SpringBootTest
class TelegramBotApplicationTests {

    @Test
    void contextLoads() throws IOException, MessagingException {
        File dir = new File("T:\\deeplearning\\imgs\\");
        File afterFile = new File("T:\\User\\Desktop\\公司\\telegram_bot\\src\\main\\resources\\imgs\\1.png");
        File[] files = dir.listFiles(pathname -> {
            // 判断文件是否是图片文件
            return pathname.getName().endsWith(".jpg")
                    && pathname.getName().trim().contains("13353386064251441");
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

            int rows = (int) Math.ceil((double) resizedImageList.size() / 3); //2
            int columns = Math.min(resizedImageList.size(), 3); //3
            BufferedImage mergedImage = new BufferedImage(desiredWidth * columns, desiredHeight * rows, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2dMerged = mergedImage.createGraphics();
           /* for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    int index = i * rows + j;
                    if (index < resizedImageList.size()) {
                        BufferedImage resizedImage = resizedImageList.get(index);
                        g2dMerged.drawImage(resizedImage, i * desiredWidth, j * desiredHeight, null);
                        resizedImage.flush();
                    }
                }
            }*/
            int index = 0;
            //rows:2  columns:3
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (index < resizedImageList.size()) {
                        BufferedImage resizedImage = resizedImageList.get(index);
                        g2dMerged.drawImage(resizedImage, j * desiredWidth, i * desiredHeight, null);
                        resizedImage.flush();
                    }
                    index++;
                }
            }


            g2dMerged.dispose();
            ImageIO.write(mergedImage, "png", afterFile);
        }

    }

}
