package com.example.telegram_bot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

class TelegramBotApplicationTests {

    @Test
    void contextLoads() throws IOException {
        File dir = new File("T:\\User\\Desktop\\公司\\telegram_bot\\src\\main\\resources\\imgs"); // 指定要搜索的文件夹路径
        String query = "13449343601146717"; // 模糊搜索的字符串

        File[] files = dir.listFiles(pathname -> {
            // 判断文件是否是图片文件
            return pathname.isFile() && (
                    pathname.getName().endsWith(".jpg") ||
                            pathname.getName().endsWith(".jpeg") ||
                            pathname.getName().endsWith(".png")
            ) && pathname.getName().contains(query); // 判断文件名是否包含指定字符串
        });

        // 将搜索到的图片文件添加到 ArrayList 中

        // Set the desired width and height for the resized images
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

        // Use the resized images for the merged image
        int rows = (int) Math.ceil((double) resizedImageList.size() / 3);
        int columns = Math.min(resizedImageList.size(), 3);
        BufferedImage mergedImage = new BufferedImage(desiredWidth * columns, desiredHeight * rows, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2dMerged = mergedImage.createGraphics();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                int index = i * rows + j;
                if (index < resizedImageList.size()) {
                    BufferedImage resizedImage = resizedImageList.get(index);
                    g2dMerged.drawImage(resizedImage, i * desiredWidth, j * desiredHeight, null);
                    resizedImage.flush();
                }
            }
        }
        g2dMerged.dispose();

        ImageIO.write(mergedImage, "png", new File("merged.png"));
    }

}
