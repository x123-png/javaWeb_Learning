package cn.edu.swu.ws.auth;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Java 验证码生成工具类
 * 支持：数字验证码、字母验证码、混合字符验证码
 * 可自定义长度、图片尺寸、干扰线、字体等
 */
public class CaptchaGenerator {
    // 验证码类型枚举
    public enum CaptchaType {
        NUMERIC,       // 纯数字
        ALPHABETIC,    // 纯字母（大小写混合）
        ALPHANUMERIC   // 字母+数字混合
    }

    // 默认配置
    private static final int DEFAULT_WIDTH = 120;    // 图片宽度
    private static final int DEFAULT_HEIGHT = 40;    // 图片高度
    private static final int DEFAULT_LENGTH = 4;     // 验证码长度
    private static final int DEFAULT_LINE_COUNT = 5; // 干扰线数量
    private static final String DEFAULT_FONT = "Arial"; // 默认字体
    private static final int DEFAULT_FONT_SIZE = 24; // 默认字体大小

    // 字符池
    private static final String NUMBERS = "0123456789";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALPHA_NUMERIC = NUMBERS + LETTERS;

    private final int width;
    private final int height;
    private final int length;
    private final int lineCount;
    private final String fontName;
    private final int fontSize;
    private final CaptchaType type;
    private final Random random = new Random();

    // 构造方法（使用默认配置）
    public CaptchaGenerator() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_LENGTH, DEFAULT_LINE_COUNT, DEFAULT_FONT, DEFAULT_FONT_SIZE, CaptchaType.ALPHANUMERIC);
    }

    // 全参数构造方法（自定义配置）
    public CaptchaGenerator(int width, int height, int length, int lineCount, String fontName, int fontSize, CaptchaType type) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.lineCount = lineCount;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.type = type;
    }

    /**
     * 生成验证码（返回 验证码字符串 + 图片缓冲流）
     * @return 验证码信息封装类
     */
    public CaptchaInfo generate() {
        // 1. 创建图片缓冲流
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 2. 设置背景色（浅灰色，避免与文字对比度太低）
        g.setColor(getRandomColor(240, 255));
        g.fillRect(0, 0, width, height);

        // 3. 设置字体（加粗，增加可读性）
        Font font = new Font(fontName, Font.BOLD, fontSize);
        g.setFont(font);

        // 4. 绘制干扰线（随机颜色、随机位置）
        drawInterferenceLines(g);

        // 5. 生成随机验证码字符串并绘制
        String captchaCode = generateCaptchaCode();
        drawCaptchaText(g, captchaCode);

        // 6. 绘制干扰点（增加识别难度）
        drawInterferencePoints(g);

        // 7. 释放资源
        g.dispose();

        return new CaptchaInfo(captchaCode, image);
    }

    /**
     * 生成验证码字符串
     */
    private String generateCaptchaCode() {
        StringBuilder sb = new StringBuilder();
        String charPool = getCharPool();

        for (int i = 0; i < length; i++) {
            // 从字符池中随机选取一个字符
            int index = random.nextInt(charPool.length());
            sb.append(charPool.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 根据验证码类型获取对应的字符池
     */
    private String getCharPool() {
        switch (type) {
            case NUMERIC:
                return NUMBERS;
            case ALPHABETIC:
                return LETTERS;
            case ALPHANUMERIC:
            default:
                return ALPHA_NUMERIC;
        }
    }

    /**
     * 绘制干扰线
     */
    private void drawInterferenceLines(Graphics2D g) {
        g.setStroke(new BasicStroke(1.5f)); // 线条粗细
        for (int i = 0; i < lineCount; i++) {
            // 随机颜色（深色，与背景形成对比）
            g.setColor(getRandomColor(100, 180));
            // 随机起点和终点
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 绘制验证码文字（随机位置、随机颜色、轻微旋转）
     */
    private void drawCaptchaText(Graphics2D g, String captchaCode) {
        // 每个字符的水平间距
        int charSpacing = width / (length + 1);

        for (int i = 0; i < captchaCode.length(); i++) {
            // 随机颜色（深色，避免与背景融合）
            g.setColor(getRandomColor(50, 150));

            // 随机旋转角度（-30° 到 30°）
            int rotateAngle = random.nextInt(60) - 30;
            g.rotate(Math.toRadians(rotateAngle),
                    charSpacing * (i + 1), height / 2);

            // 绘制字符（y 坐标随机偏移，增加不规则性）
            int yOffset = random.nextInt(10) - 5;
            g.drawString(String.valueOf(captchaCode.charAt(i)),
                    charSpacing * (i + 1) - 8, // x 坐标（微调居中）
                    height / 2 + fontSize / 2 + yOffset); // y 坐标

            // 恢复旋转角度
            g.rotate(-Math.toRadians(rotateAngle),
                    charSpacing * (i + 1), height / 2);
        }
    }

    /**
     * 绘制干扰点（噪点）
     */
    private void drawInterferencePoints(Graphics2D g) {
        int pointCount = width * height / 50; // 噪点数量（按图片像素比例）
        for (int i = 0; i < pointCount; i++) {
            g.setColor(getRandomColor(150, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.fillRect(x, y, 1, 1); // 绘制 1x1 的小点
        }
    }

    /**
     * 生成随机颜色（在 min 和 max 范围内）
     */
    private Color getRandomColor(int min, int max) {
        if (min > 255) min = 255;
        if (max > 255) max = 255;
        int r = min + random.nextInt(max - min);
        int g = min + random.nextInt(max - min);
        int b = min + random.nextInt(max - min);
        return new Color(r, g, b);
    }

    /**
     * 验证码信息封装类（包含验证码字符串和图片）
     */
    public static class CaptchaInfo {
        private final String code;       // 验证码字符串
        private final BufferedImage image; // 验证码图片

        public CaptchaInfo(String code, BufferedImage image) {
            this.code = code;
            this.image = image;
        }

        // 获取验证码字符串（用于后端校验）
        public String getCode() {
            return code;
        }

        // 获取图片缓冲流（用于输出到前端或文件）
        public BufferedImage getImage() {
            return image;
        }

        // 将图片写入输出流（如 HttpServletResponse）
        public void writeToStream(OutputStream outputStream) throws IOException {
            ImageIO.write(image, "png", outputStream);
        }

        // 将图片保存到本地文件
        public void saveToFile(String filePath) throws IOException {
            ImageIO.write(image, "png", new File(filePath));
        }
    }

    // ------------------------------ 测试方法 ------------------------------
    public static void main(String[] args) {
        // 1. 使用默认配置生成（4位混合字符，120x40图片）
        CaptchaGenerator defaultGenerator = new CaptchaGenerator();
        CaptchaInfo defaultCaptcha = defaultGenerator.generate();
        System.out.println("默认配置验证码：" + defaultCaptcha.getCode());
        try {
            defaultCaptcha.saveToFile("D:\\java\\default_captcha.png");
            System.out.println("默认验证码已保存到本地");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}