package cn.edu.swu.miniwebsrv.core;

import cn.edu.swu.miniwebsrv.handler.DefaultHandler;
import cn.edu.swu.miniwebsrv.handler.EchoHandler;
import cn.edu.swu.miniwebsrv.handler.TimeHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class Config {
    private final int port = 8080;   //HTTP协议默认的端口号
    private final String webRoot ="webroot"; //是一个web服务器上存放网页和静态资源的根目录
    private final Map<String, Handler> handlerMap = new HashMap<>(); //URL-Handler映射

    private Config() {
        handlerMap.put("/", new DefaultHandler());
        handlerMap.put("/time", new TimeHandler());
        handlerMap.put("/echo", new EchoHandler());
    }

    public String getWebRoot() {
        return webRoot;
    }

    private static class Holder {
        private static final Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return Holder.INSTANCE;
    }

    public int getPort() {
        return port;
    }

    public String getWebroot() {
        return webRoot;
    }

    public Handler getHandler(String url) {
        //首先查找是否存在完全匹配的url
        if (handlerMap.containsKey(url)) {
            return handlerMap.get(url);
        }

        //基础路径匹配   entry是键值对对象           返回handlerMap中的所有键值对
        for (Map.Entry<String, Handler> entry : handlerMap.entrySet()) {
            String basePath = entry.getKey();  //从handlerMap中获取url路径
            if (url.startsWith(basePath) && !url.substring(basePath.length()).contains("/")) {  //获取符合以该url开头的路径而不是嵌套路径
                return entry.getValue();
            }
        }

        return handlerMap.get(url);
    }
}
