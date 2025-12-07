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

@WebServlet(urlPatterns = "/x123-png/books")
public class BookServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServletContext context = request.getServletContext();
        DatabaseService service = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        try {
            List<Book> books = service.query(
                    "select * from book", new BookResultSetVisitor());

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
