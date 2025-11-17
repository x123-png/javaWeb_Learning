package cn.edu.swu.ws.controller;

import cn.edu.swu.ws.db.BookResultSetVisitor;
import cn.edu.swu.ws.db.DatabaseService;
import cn.edu.swu.ws.entity.Book;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/books")
public class ShowBookServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServletContext context = request.getServletContext();
        DatabaseService databaseService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        try {
            List<Book> books = databaseService.query(
                    "select * from book", new BookResultSetVisitor());
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            try(Writer writer = response.getWriter()) {
                String html = HtmlHelper.wrapBody(this.buildBookTable(books));
                writer.write(html);
                writer.flush();
            }
        } catch (IOException | SQLException e) {
            throw new ServletException(e);
        }
    }



    private String buildBookTable(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table id='book-table'>");
        sb.append("<tr><th>编号</th><th>书名</th><th>作者</th>" +
                "<th>价格</th><th>备注</th><th>日期</th><th></th><th></th></tr>");
        for(Book book : books) {
            sb.append("<tr>");
            sb.append("<td>").append(book.getId()).append("</td>");
            sb.append("<td>").append(book.getName()).append("</td>");
            sb.append("<td>").append(book.getAuthor()).append("</td>");
            sb.append("<td>").append(book.getPrice()).append("</td>");
            sb.append("<td>").append(book.getMemo()).append("</td>");
            sb.append("<td>").append(book.getPublish()).append("</td>");
            sb.append("<td>").append("<a href='./deleteBook?id=" + book.getId() +"'>删除</a>").append("</td>");
            sb.append("<td>").append("<a href='./updateBook?id=" + book.getId() +"'>修改</a>").append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}
