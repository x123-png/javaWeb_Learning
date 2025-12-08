package cn.edu.swu.ws.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/captcha")
public class CaptchaServlet extends HttpServlet {
    public final static String CAPTCHA_KEY = "CAPTCHA_KEY";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 使用默认配置生成（4位混合字符，120x40图片）
        CaptchaGenerator defaultGenerator = new CaptchaGenerator();
        CaptchaGenerator.CaptchaInfo captcha = defaultGenerator.generate();

        HttpSession session = request.getSession(true);
        session.setAttribute(CAPTCHA_KEY, captcha.getCode());

        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("image/png");
            captcha.writeToStream(outputStream);
            outputStream.flush();
        }
    }

}
