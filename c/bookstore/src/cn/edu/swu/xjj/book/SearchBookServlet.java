package cn.edu.swu.xjj.book;

import cn.edu.swu.xjj.repo.BookResultSetVisitor;
import cn.edu.swu.xjj.repo.DatabaseService;
import cn.edu.swu.xjj.utils.HtmlHelper;
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

@WebServlet("/x123-png/searchBook")
public class SearchBookServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String content = request.getParameter("content");
        ServletContext context = request.getServletContext();
        DatabaseService dbService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);

        String sql = "select * from book where name like '%" + content + "%' or author like '%" + content + "%'";
        try {
            List<Book> books = dbService.query(sql, new BookResultSetVisitor());
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            try(Writer writer = response.getWriter()) {
                String table = HtmlHelper.buildBooksTable(books);
                String html = HtmlHelper.wrapHtml(table);
                writer.write(html);
                writer.flush();
            }
        } catch (IOException | SQLException e) {
            throw new ServletException(e);
        }
    }

}
