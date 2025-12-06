package cn.edu.swu.ws.auth;

import cn.edu.swu.ws.repo.DatabaseService;
import com.sun.net.httpserver.HttpContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String code = request.getParameter("code");

        // 首先进行验证码的比对
        HttpSession session = request.getSession(true);
        String captcha = (String)session.getAttribute(CaptchaServlet.CAPTCHA_KEY);
        if (captcha == null || !captcha.equalsIgnoreCase(code)) {
            response.sendRedirect("./login.html");
            return;
        }

        // 构造一个查询SQL
        String template = "select * from user where name='%s' and passwd=md5('%s')";
        // 从容器上下文获取数据库访问的服务类
        ServletContext context = request.getServletContext();
        DatabaseService dbService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        // 执行SQL，判断用户名密码是否正确
        try {
            List<User> users = dbService.query(String.format(template, user, pass), new UserResultSetVisitor());
            if (users.size() > 0) {
                this.loginToken(request, users.get(0));
                response.sendRedirect("./admin/books");
            } else {
                response.sendRedirect("./login.html");
            }
        } catch (IOException | SQLException e) {
            throw new ServletException(e);
        }

    }

    private void loginToken(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(AuthFilter.LOGIN_TOKEN_KEY, user);
    }

}
