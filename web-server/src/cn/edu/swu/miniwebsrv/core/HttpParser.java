package cn.edu.swu.miniwebsrv.core;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    public static Parameter parse(InputStream inputStream) throws IOException {
        Parameter parameter = new Parameter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //解析HTTP请求行
        String requestLine = reader.readLine();
        if(requestLine != null) {
            String[] requestLineParts = requestLine.split(" ");
            if (requestLineParts.length >= 3) {
                // 设置请求方法
                parameter.setMethod(requestLineParts[0]);
                // 设置请求URL，并解析 URL 参数
                String url = requestLineParts[1];
                parameter.setUrl(url);

                // 解析 URL 参数
                int queryIndex = url.indexOf("?");
                if (queryIndex != -1) {
                    String query = url.substring(queryIndex + 1); // 获取 `param=value` 这一部分
                    String[] queryParams = query.split("&");
                    for (String param : queryParams) {
                        String[] keyValue = param.split("=");
                        if (keyValue.length == 2) {
                            // URL 解码，防止 URL 编码的字符
                            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                            parameter.getUrlParams().put(key, value);
                        }
                    }
                }
            }
        }

        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] headerParts = line.split(": ", 2);
            if (headerParts.length == 2) {
                String headerName = headerParts[0].trim();
                String headerValue = headerParts[1].trim();
                parameter.setHeader(headerName, headerValue);
            }
        }

        String method = parameter.getMethod();
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) {
            // 解析请求体
            String contentLengthHeader = parameter.getHeader("Content-Length");
            if (contentLengthHeader != null) {
                int contentLength = Integer.parseInt(contentLengthHeader);
                char[] bodyBuffer = new char[contentLength];
                reader.read(bodyBuffer, 0, contentLength);
                String body = new String(bodyBuffer);

                // 假设请求体是 x-www-form-urlencoded 格式 (如表单数据)
                String[] bodyParams = body.split("&");
                for (String param : bodyParams) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                        String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                        parameter.setBodyParams(key, value);
                    }
                }
            }
        }

        return parameter;
    }

    public static String urlDecode(String encoded) {
        try {
            return URLDecoder.decode(encoded, "UTF-8");
        } catch (UnsupportedEncodingException e){
            return encoded;
        }
    }
}
