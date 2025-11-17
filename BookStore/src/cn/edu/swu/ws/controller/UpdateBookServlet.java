package cn.edu.swu.ws.controller;

import cn.edu.swu.ws.db.BookResultSetVisitor;
import cn.edu.swu.ws.db.DatabaseService;
import cn.edu.swu.ws.entity.Book;
import cn.edu.swu.ws.utils.HtmlHelper;
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

@WebServlet(urlPatterns = "/updateBook")
public class UpdateBookServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String id = request.getParameter("id");

        ServletContext context = this.getServletContext();
        DatabaseService dbService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        String selectSQL = "select * from book where id = " + id;

        try {
            List<Book> books = dbService.query(selectSQL, new BookResultSetVisitor());
            Book b = books.get(0);
            String template = """
    <form action="./updateBook" method="post" style="line-height:3em">
        <input type="hidden" name="id" value="%s"><br>
        书名：<input type="text" name="name" value="%s"><br>
        作者：<input type="text" name="author" value="%s"><br>
        价格：<input type="text" name="price" value="%s"><br>
        备注：<textarea rows="3" cols="22" name="memo">%s</textarea><br>
        日期：<input type="text" name="publish" value="%s"><br><br>
        <input type="submit" value="修改图书">
    </form>
        """;
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            try(Writer writer = response.getWriter()) {
                String form = String.format(template,
                        b.getId(), b.getName(), b.getAuthor(), b.getPrice(), b.getMemo(), b.getPublish());
                String html = HtmlHelper.wrapHtml(form);
                writer.write(html);
                writer.flush();
            }
        } catch (IOException | SQLException e) {
            throw new ServletException(e);
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String price = request.getParameter("price");
        String memo = request.getParameter("memo");
        String publish = request.getParameter("publish");

        ServletContext context = this.getServletContext();
        DatabaseService dbService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        String updateSQL = """
        update book set name='%s', author='%s', price=%s, memo='%s', publish='%s' where id=%s        
        """;

        try {
            dbService.execute(String.format(updateSQL, name, author, price, memo, publish, id));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("./books");
    }

}
