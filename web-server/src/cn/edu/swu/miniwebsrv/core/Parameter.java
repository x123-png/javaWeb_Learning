package cn.edu.swu.miniwebsrv.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Parameter {
    private String method = null;  //HTTP请求方法
    private String url = null;     //请求的URL地址，一般在HTTP请求的第一行，通常由协议+域名+路径+查询参数（可选）构成，通常在GET请求中出现，用于传递过滤条件，分页信息等数据
    private Map<String, String> urlParams = new HashMap<>(); //存储URL中的查询参数
    private Map<String, String> bodyParams = new HashMap<>(); //存储请求体参数，Body（请求体），用于传输实际的内容数据，常用于POST,PUT,PATCH等HTTP方法中，与URL参数不同，Body参数用于传输更大或者更复杂的数据（如表单，JSON,XML。文件等）
    private Map<String, String> headers = new HashMap<>();  //http请求头

    //构造函数
    public Parameter(){};
    public Parameter(String method, String url){
        this.method = method;
        this.url = url;
    }
    public Parameter(String method,String url,Map<String, String> urlParams,Map<String, String> bodyParams,Map<String, String> headers){
        this.method = method;
        this.url = url;
        this.urlParams = urlParams;
        this.bodyParams = bodyParams;
        this.headers = headers;
    }

    //返回一个包含所有url参数和body参数的不可修改（unmodified）的集合
    public Map<String, String> getAllParams() {
        Map<String, String> allParams = new HashMap<>();
        allParams.putAll(urlParams);
        allParams.putAll(bodyParams);
        return Collections.unmodifiableMap(allParams);
    }

    //根据参数名name查找并返回请求中的参数值
    public String getParama(String name) {
        if (bodyParams.containsKey(name)) {
            return bodyParams.get(name);
        }
        return urlParams.get(name);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders(){
        return headers;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBodyParams(String key, String value) {
        bodyParams.put(key, value);
    }

    public String getHeader(String key){
        return headers.get(key);
    }

    public Map<String, String>  getUrlParams() {
        return urlParams;
    }
}
