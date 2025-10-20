package cn.edu.swu.miniwebsrv.handler;

import cn.edu.swu.miniwebsrv.core.Parameter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class EchoHandler extends Handler {

    public void handler(Parameter request, OutputStream response) {
        PrintWriter writer = new PrintWriter(response);
        //获取参数
        Map<String, String> params = request.getAllParams();

        //创建html内容
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("<h1>Echo Server Response</h1>");
        contentBuilder.append("<h2>Request Details:</h2>");
        contentBuilder.append("<p>Method: ").append(request.getMethod()).append("</p>");

        //根据接收到的参数Map构建HTML列表，回显给客户端
        if (params.isEmpty()) {
            contentBuilder.append("<h2>No Parameters Received.</h2>");
        } else {
            contentBuilder.append("<h2>Received Parameters:</h2>");
            contentBuilder.append("<ul>");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                contentBuilder.append("<li><strong>").append(entry.getKey())
                        .append("</strong>: ").append(entry.getValue()).append("</li>");
            }
            contentBuilder.append("</ul>");
        }

        String content = contentBuilder.toString();

        //构造完整的 HTTP 响应头
        writer.println("HTTP/1.1 200 OK");  //状态行
        writer.println("Content-Type: text/html; charset=UTF-8"); //响应类型
        writer.println("Content-Length: " + content.getBytes().length); //内容长度
        writer.println("Connection: close"); //关闭连接
        writer.println();  //空行分隔头部和响应体

        //输出响应体
        writer.println(content);
        writer.flush();
    }

    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
