package cn.edu.swu.ws.book;

import cn.edu.swu.ws.repo.DatabaseService;
import cn.edu.swu.ws.repo.ResultSetVisitor;
import cn.edu.swu.ws.utils.HtmlHelper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/books")
public class BookServlet extends HttpServlet {

    // http://localhost:8080/bookstore/admin/books?page=1&size=3

    public final static int DEFAULT_PAGE_SIZE = 5;
    public final static int DEFAULT_PAGE = 1;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String inputPage = request.getParameter("page");
        String inputSize = request.getParameter("size");
        int page = (inputPage != null) ? Integer.parseInt(inputPage) : DEFAULT_PAGE;
        int size = (inputSize != null) ? Integer.parseInt(inputSize) : DEFAULT_PAGE_SIZE;

        ServletContext context = request.getServletContext();
        DatabaseService service = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        try {
            String sql = String.format("SELECT * FROM book ORDER BY id DESC LIMIT %s OFFSET %s", size, (page - 1) * size);
            List<Book> books = service.query(sql , new BookResultSetVisitor());
            int pages = this.totalPages(service, size);

            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            try(Writer writer = response.getWriter()) {
                String table = HtmlHelper.buildBooksTable(books);
                String pager = HtmlHelper.createPager("./books?", pages, page, size);
                String html = HtmlHelper.wrapHtml(table + "\n<br><br>\n" + pager);
                writer.write(html);
                writer.flush();
            }
        } catch (IOException | SQLException e) {
            throw new ServletException(e);
        }
    }


    private int totalPages(DatabaseService service, int size) throws SQLException {
        String sql = String.format("SELECT CEIL(COUNT(*)/%s) AS pages FROM book;", size);
        // 编写一个匿名的 ResultSetVisitor 接口的实现类
        List<Integer> pages = service.query(sql, new ResultSetVisitor<Integer>() {
            @Override
            public List<Integer> visit(ResultSet rs) throws SQLException {
                rs.next();
                return List.of(rs.getInt("pages"));
            }
        });
        return pages.get(0);
    }

}
