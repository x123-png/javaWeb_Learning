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

@WebServlet(urlPatterns = "/updateBook")
public class UpdateBookServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String id = request.getParameter("id");

        ServletContext context = this.getServletContext();
        DatabaseService databaseService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        String sql = "select * from book where id = " + id;

        try {
            List<Book> books = databaseService.query(sql, new BookResultSetVisitor());
            Book book = books.get(0);

            String template = """
                <form action="./updateBook" method="post">
                    <input type="hidden" name="id" value="%s">
                    书名：<input type="text" name="name" value="%s"><br/><br/>
                    作者：<input type="text" name="author" value="%s"><br/><br/>
                    价格：<input type="text" name="price" value="%s"><br/><br/>
                    备注：<input type="text" name="memo" value="%s"><br/><br/>
                    日期：<input type="text" name="publish" value="%s"><br/><br/>
                    <input type="submit" value="更新图书"/>
                </form>
            """;

            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            try(Writer writer = response.getWriter()) {
                String form = String.format(template, book.getId(), book.getName(), book.getAuthor(), book.getPrice(),
                        book.getMemo(), book.getPublish());
                String html = HtmlHelper.wrapBody(form);
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
        Double price = Double.parseDouble(request.getParameter("price"));
        String memo = request.getParameter("memo");
        String publish = request.getParameter("publish");

        String template = "update book set name='%s', author='%s', price=%s, memo='%s', publish='%s' where id=%s";

        ServletContext context = this.getServletContext();
        DatabaseService databaseService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        try {
            databaseService.execute(String.format(template, name, author, price, memo, publish, id));
            response.sendRedirect("./books");
        } catch (SQLException e) {
            throw new ServletException(e);
        }

    }

}
