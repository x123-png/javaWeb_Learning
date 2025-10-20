package cn.edu.swu.miniwebsrv.handler;

import cn.edu.swu.miniwebsrv.core.Parameter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TimeHandler extends Handler {
    public  void handler(Parameter request, OutputStream response){
        PrintWriter writer = new PrintWriter(response);
        //获取当前时间你
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String content = "<h1>MiniWebSvr Current Time</h1>" +
                "<p>Server Time: <strong>" + currentTime + "</strong></p>";

        // 构造完整的 HTTP 响应头和响应体
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println("Content-Length: " + content.getBytes().length);
        writer.println("Connection: close");
        writer.println();

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
