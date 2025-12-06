package cn.edu.swu.ws.utils;

import cn.edu.swu.ws.book.Book;

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
            <a href="./books"> 显示图书 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="./logout"> 退出系统 </a>
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
                .append("<th>编号</th><th>名字</th><th>作者</th><th>价格</th>" +
                        "<th>备注</th><th>日期</th><th>图片</th><th></th><th></th>")
                .append("</tr>");
        String template = """
            <tr>
                <td align='center'>%s</td>
                <td>%s</td>
                <td align='center'>%s</td>
                <td align='center'>%s</td>
                <td>%s</td>
                <td align='center'>%s</td>
                <td align='center'>%s</td>
                <td align='center'><a href='./deleteBook?id=%s'>删除</a></td>
                <td align='center'><a href='./updateBook?id=%s'>修改</a></td>
            </tr>
        """;
        for (Book b : books) {
            sb.append(String.format(template,
                    b.getId(), b.getName(), b.getAuthor(), b.getPrice(), b.getMemo(), b.getPublish(),
                    (b.getPicture() == null? "" :
                            String.format("<img src='../upload/%s' width='80px'/>", b.getPicture())),
                    b.getId(), b.getId()
            ));
        }
        sb.append("</table>");
        return sb.toString();
    }

    public static String createPager(String url, int pages, int page, int size) {
        //  <上一页   1，2，3，4，5  下一页>
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='pager'>\n");
        if (page != 1) {
            sb.append(String.format("<a href='%s&page=%s&size=%s'>", url, page-1, size))
                    .append("<上一页").append("</a>&nbsp;&nbsp;&nbsp;&nbsp;\n");
        }
        for (int i=1; i<=pages; i++) {
            if (i == page) {
                sb.append("<b>").append(i).append("</b>&nbsp;&nbsp;\n");
            } else {
                sb.append(String.format("<a href='%s&page=%s&size=%s'>\n", url, i, size)).append(i).append("</a>&nbsp;&nbsp;\n");
            }
        }
        if (page != pages) {
            sb.append(String.format("&nbsp;&nbsp;&nbsp;&nbsp;<a href='%s&page=%s&size=%s'>", url, page+1, size)).append("下一页>").append("</a>\n");
        }
        sb.append("</div>\n");
        return sb.toString();
    }

}
