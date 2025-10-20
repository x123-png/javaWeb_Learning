package cn.edu.swu.miniwebsrv.handler;

import cn.edu.swu.miniwebsrv.core.Config;
import cn.edu.swu.miniwebsrv.core.Parameter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class DefaultHandler extends Handler {
    public void handler(Parameter request, OutputStream response) {
        // 1. 获取请求路径（可能需要将 / 映射到 index.html）
        String requestUrl = request.getUrl().equals("/") ? "/index.html" : request.getUrl();
        // 2. 根据 Config.getWebRoot() 构建完整文件路径
        Path filePath = Paths.get(Config.getInstance().getWebRoot(), request.getUrl().equals("/") ? "index.html" : request.getUrl().substring(1));

        try {
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                byte[] fileBytes = Files.readAllBytes(filePath);

                //确定并设置正确的 Content-Type (例如 text/html, image/jpeg 等)
                String contentType = Files.probeContentType(filePath) != null ? Files.probeContentType(filePath) : "application/octet-stream";
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                // 写入 200 OK 响应头
                response.write(("HTTP/1.1 200 OK\r\n").getBytes());
                response.write(("Content-Type: " + contentType + "\r\n").getBytes());
                response.write(("Content-Length: " + fileBytes.length + "\r\n").getBytes());
                response.write(("\r\n").getBytes());

                // 写入文件内容
                response.write(fileBytes);
            } else {
                // 文件不存在或路径错误，返回 404
                String content = "<h1>404 Not Found</h1><p>The requested resource " + request.getUrl() + " was not found.</p>";
                response.write(("HTTP/1.1 404 Not Found\r\n").getBytes());
                response.write(("Content-Type: text/html; charset=UTF-8\r\n").getBytes());
                response.write(("Content-Length: " + content.getBytes().length + "\r\n").getBytes());
                response.write(("\r\n").getBytes());
                response.write(content.getBytes());
            }
        } catch (IOException e) {
            //文件读取异常，返回 500 Internal Server Error
            try {
                String content = "<h1>500 Internal Server Error</h1><p>There was an error processing your request.</p>";
                response.write("HTTP/1.1 500 Internal Server Error\r\n".getBytes());
                response.write("Content-Type: text/html; charset=UTF-8\r\n".getBytes());
                response.write(("Content-Length: " + content.getBytes().length + "\r\n").getBytes());
                response.write("\r\n".getBytes());
                response.write(content.getBytes());
            } catch (IOException ex) {
                // 忽略进一步的 IO 错误
            }
        } finally {
            try {
                response.flush();
            } catch (IOException e) { /* ignore */ }
        }
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
