package cn.edu.swu.miniwebserver.core;

import org.xml.sax.helpers.DefaultHandler;

import cn.edu.swu.miniwebserver.handler.DefaultHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class Config {
    private final int port = 8080;   //HTTP协议默认的端口号
    private final String webroot ="webroot"; //是一个web服务器上存放网页和静态资源的根目录
    private final Map<String, Handler> handlerMap = new HashMap<>(); //URL-Handler映射

    private Config() {
        handlerMap.put("/", new DefaultHandler());
        handlerMap.put("/time", new TimeHandler());
        handlerMap.put("/echo", new EchoHandler());
    }
}
