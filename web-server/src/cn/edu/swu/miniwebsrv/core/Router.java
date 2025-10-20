package cn.edu.swu.miniwebsrv.core;

import cn.edu.swu.miniwebsrv.handler.*;
import java.io.OutputStream;

public class Router {
    public void route(Parameter request, OutputStream response) {
        Config config = Config.getInstance();

        Handler handler = (Handler) config.getHandler(request.getUrl());

        if (handler == null) {
            // 如果没有匹配的路由，使用 DefaultHandler 处理
            handler = (Handler) config.getHandler("/");
        }

        if (handler != null) {
            handler.handle(request, response);
        } else {
            try {
                response.write("HTTP/1.1 500 Internal Server Error\r\n".getBytes());
                response.write("Content-Type: text/plain\r\n".getBytes());
                response.write("\r\n".getBytes());
                response.write("500 Internal Server Error: No handler found.\r\n".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
