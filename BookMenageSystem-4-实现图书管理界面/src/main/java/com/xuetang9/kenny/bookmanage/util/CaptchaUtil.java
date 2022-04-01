package com.xuetang9.kenny.bookmanage.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 生成验证码图片的工具类
 */
public class CaptchaUtil {
    public static String CAPTCHA = "captcha";
    public static String IMAGE = "image";
    private static int decimal = 6; //验证码的位数
    //无数字0、1, 字母o, l
    private static char[] charArray = {
            '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    /**
     * 键值对：验证码 -> 对应的图像
     * @param width
     * @param height
     * @return
     */
    public static Map<String, Object> generateCaptcha(int width, int height){
        Map<String, Object> captchaMap = new HashMap<>();
        WritableImage image1 = new WritableImage(width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = image.getGraphics();
        g.setColor(GetRandColor(200, 255));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        //随机产生100条干扰线，使图像中的验证码不容易被其他程序侦测到
        g.setColor(GetRandColor(160, 200));
        Random random = new Random();
        for(int i = 0; i < 100; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        StringBuffer captcha = new StringBuffer();
        //生成验证码字符串
        for(int i = 0; i < decimal; i++){
            char currCode = charArray[(int)(charArray.length * Math.random())];
            captcha.append(currCode);
            //将验证码显示在图像中
            g.setColor(GetRandColor(20, 180));
            //每个字符间距10像素
            g.drawString(Character.toString(currCode), i * 15 + 10, 23);
        }

        SwingFXUtils.toFXImage(image, image1);

        captchaMap.put("captcha", captcha.toString());
        captchaMap.put("image", image1);
        return captchaMap;
    }

    private static Color GetRandColor(int from, int to){
        Random random = new Random();
        if(from > 255) from = 255;
        if(to > 255) to = 255;
        int r = from + random.nextInt(to - from);
        int g = from + random.nextInt(to - from);
        int b = from + random.nextInt(to - from);
        return new Color(r, g, b);
    }
}
