package cn.edu.swu.xjj;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageServlet extends HttpServlet {
    // 常量：用于在 ServletContext 中存储留言列表的 Key
    private static final String MESSAGE_LIST_KEY = "messageList";

    /**
     * 【生命周期方法】在 Servlet 第一次初始化时调用
     * 用于初始化共享的留言列表
     */
    @Override
    public void init() throws ServletException {
        // 获取应用上下文对象
        ServletContext context = this.getServletContext();

        // 检查留言列表是否已存在（防止多次初始化）
        if (context.getAttribute(MESSAGE_LIST_KEY) == null) {
            // 使用 Collections.synchronizedList 确保线程安全（进阶点）
            List<Message> messageList = Collections.synchronizedList(new ArrayList<>());

            // 添加一个示例留言
            messageList.add(new Message("系统管理员", "欢迎使用留言板！请开始您的实验。"));

            // 将列表存入应用范围 (ServletContext)
            context.setAttribute(MESSAGE_LIST_KEY, messageList);
            System.out.println("留言列表初始化完成，存储于 ServletContext。");
        }
    }

    /**
     * 【核心】处理 HTTP POST 请求：接收并保存新留言
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 设置请求编码，处理中文乱码
        request.setCharacterEncoding("UTF-8");

        // 2. 获取 POST 参数
        String name = request.getParameter("name");
        String content = request.getParameter("message");

        // 简单的输入校验
        if (name != null && !name.trim().isEmpty() && content != null && !content.trim().isEmpty()) {

            // 3. 封装 Message 对象
            Message newMessage = new Message(name.trim(), content.trim());

            // 4. 获取共享的留言列表并添加新留言
            ServletContext context = this.getServletContext();
            @SuppressWarnings("unchecked")
            List<Message> messageList = (List<Message>) context.getAttribute(MESSAGE_LIST_KEY);

            // 留言添加到列表的头部 (索引 0)，实现最新留言在上面
            messageList.add(0, newMessage);
        }

        // 5. 【关键】处理完数据后，重定向到 GET 请求，防止 F5 刷新导致重复提交
        response.sendRedirect("message");
    }

    /**
     * 【核心】处理 HTTP GET 请求：展示所有历史留言和输入表单
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 设置响应编码，确保输出的 HTML 页面正确显示中文
        response.setContentType("text/html;charset=UTF-8");

        // 2. 获取共享的留言列表
        ServletContext context = this.getServletContext();
        @SuppressWarnings("unchecked")
        List<Message> messageList = (List<Message>) context.getAttribute(MESSAGE_LIST_KEY);

        // 3. 开始构建 HTML 响应
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html><head><title>在线留言板 - 实验</title>");
        // 提示学生可以在这里链接 style.css
        response.getWriter().println("<link rel='stylesheet' type='text/css' href='style.css'>");
        response.getWriter().println("</head><body>");

        response.getWriter().println("<h1>留言板 (Servlet 实验)</h1>");

        // 4. 【展示表单】学生需在 index.html 中实现此表单，这里为了方便直接嵌入  上方留言板
        response.getWriter().println("<h2>留下您的印记</h2>");
        response.getWriter().println("<form action='message' method='POST' id='msg-form'>");
        response.getWriter().println("昵称: <input type='text' name='name' required><br>");
        response.getWriter().println("留言: <textarea name='message' rows='4' cols='50' required></textarea><br>");
        response.getWriter().println("<input type='submit' value='提交留言'>");
        response.getWriter().println("</form>");

        response.getWriter().println("<hr>");

        // 5. 【展示历史留言】  下方留言记录
        response.getWriter().println("<h2>历史留言 (" + messageList.size() + " 条)</h2>");
        response.getWriter().println("<table border='1' width='80%' id='msg-table'>");
        response.getWriter().println("<tr><th>昵称</th><th>留言内容</th><th>时间</th></tr>");

        // 遍历列表，动态生成表格行
        for (Message msg : messageList) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + msg.getName() + "</td>");
            response.getWriter().println("<td>" + msg.getContent() + "</td>");
            response.getWriter().println("<td>" + msg.getFormattedTimestamp() + "</td>"); // 使用带时间戳的方法
            response.getWriter().println("</tr>");
        }

        response.getWriter().println("</table>");
        response.getWriter().println("</body></html>");
    }
}
