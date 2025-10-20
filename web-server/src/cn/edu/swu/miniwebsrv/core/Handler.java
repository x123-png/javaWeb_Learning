package cn.edu.swu.miniwebsrv.core;

import java.io.OutputStream;
import java.lang.reflect.Parameter;
//该接口作用是处理HTTP请求并生成响应返回给客户端
public interface Handler {  //创建接口，是一个抽象类型，用来定义某些行为但不提供具体的实现方法，而在实现它的类提供具体的行为实现
    void handler(Parameter request, OutputStream response) ; //Parameter类型表示请求数据，response作为HTTP中的响应数据

    void handle(cn.edu.swu.miniwebsrv.core.Parameter request, OutputStream response);
}
