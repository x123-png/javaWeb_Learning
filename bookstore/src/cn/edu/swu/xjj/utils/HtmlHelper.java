package cn.edu.swu.xjj.utils;

import cn.edu.swu.xjj.book.Book;

import java.util.List;

public class HtmlHelper {

    public static String wrapHtml(String content) {
        String template = """
<html>
    <head>
      <meta charset="utf-8">
      <title>西大网上书城</title>
      <link rel="stylesheet" href="../css/bookstore.css" />
    </head>
<body>
    <center>
        <br><h1>欢迎访问西大网上书城</h1>
        <div>
            <a href="./add_book.html"> 添加图书 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="./add_book.html"> 显示图书 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="./logout"> 退出系统 </a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
        <br>
        <form action="./searchBook" method="get">
            <input type="text" name="content"> &nbsp;&nbsp;&nbsp; <input type="submit" value="查询">
        </form>
        %s
    </center>
</body>
</html>
        """;
        return String.format(template, content);
    }


    /**
     * 该函数将传入的图书列表对象转换成 html 的表格
     * @param books
     * @return
     */
    public static String buildBooksTable(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table class='tb-book'>");
        sb.append("<tr>")
                .append("<th>编号</th><th>名字</th><th>作者</th><th>价格</th><th>备注</th><th>日期</th><th></th><th></th>")
                .append("</tr>");
        String template = """
            <tr>
                <td align='center'>%s</td>
                <td>%s</td>
                <td align='center'>%s</td>
                <td align='center'>%s</td>
                <td>%s</td>
                <td align='center'>%s</td>
                <td align='center'><a href='./deleteBook?id=%s'>删除</a></td>
                <td align='center'><a href='./updateBook?id=%s'>修改</a></td>
            </tr>
        """;
        int seqNum = 1;
        for (Book b : books) {
            sb.append(String.format(template,
                    seqNum, b.getName(), b.getAuthor(), b.getPrice(), b.getMemo(), b.getPublish(),
                    b.getId(), b.getId()
            ));
            seqNum++;
        }
        sb.append("</table>");
        return sb.toString();
    }
}
