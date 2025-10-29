package com.znv.manage.common.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 安全测试：后端生成验证码
 * @author ：ygr
 * @date ：Created in 2021-1-7
 */

public class VerifyCode {

    private static Random random = new Random();

    public static  String drawRandomText(int width, int height, BufferedImage verifyImg) {
        Graphics2D graphics = (Graphics2D)verifyImg.getGraphics();
        graphics.setColor(Color.WHITE);//设置画笔颜色-验证码背景色
        graphics.fillRect(0, 0, width, height);//填充背景
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 40));
        //数字和字母的组合
        String baseNumLetter = "1234567890";

        StringBuffer sBuffer = new StringBuffer();
        int x = 10;  //旋转原点的 x 坐标
        String ch = "";
        for(int i = 0;i < 4;i++){
            graphics.setColor(getRandomColor());
            //设置字体旋转角度
            int degree = random.nextInt() % 40;  //角度小于30度
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            sBuffer.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 45);
            graphics.drawString(ch, x, 45);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 45);
            x += 48;
        }
        //画干扰线
        for (int i = 0; i <6; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }
        //添加噪点
        for(int i=0;i<30;i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 3,3);
        }
        return sBuffer.toString();
    }
    /**
     * 随机取色

     */
    private static Color getRandomColor() {

        Color color = new Color(random.nextInt(256),
                random.nextInt(256), random.nextInt(256));
        return color;
    }

    private static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i=0; i<bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
            return ret.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String createToken() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return MD5(uuid);
    }

    public static void main(String[] args) {
        System.out.println("args = " + random.nextInt(256));
        System.out.println("args = " + random.nextInt(256));
        System.out.println("args = " + random.nextInt(256));
    }
}
