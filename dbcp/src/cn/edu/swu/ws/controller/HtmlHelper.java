package cn.edu.swu.ws.controller;

public class HtmlHelper {

    public static String wrapBody(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<link rel='stylesheet' href='css/bookstore.css'>");
        sb.append("</head>");
        sb.append("<body><center>");
        sb.append("<br><h1>欢迎访问西大网上书店</h1>");
        sb.append("<br><a href='./addBook.html'>添加图书</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        sb.append("<a href='./books'>显示图书</a><br><br>");
        sb.append(content);
        sb.append("</center></body>");
        sb.append("</html>");

        return sb.toString();
    }

}
