package cn.edu.swu.xjj.book;

import cn.edu.swu.xjj.repo.BookResultSetVisitor;
import cn.edu.swu.xjj.repo.DatabaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/getBookInfo")
public class GetBookInfoServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();

    public void init() throws ServletException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.objectMapper.setDateFormat(dateFormat);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取图书ID参数
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"缺少图书ID参数\"}");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(idParam);
            if (bookId <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"图书ID必须是正整数\"}");
                return;
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"图书ID参数格式错误\"}");
            return;
        }

        ServletContext context = request.getServletContext();
        DatabaseService service = (DatabaseService) context.getAttribute(DatabaseService.CONTEXT_KEY);
        
        try {
            String sql = "SELECT * FROM book WHERE id = " + bookId;
            List<Book> books = service.query(sql, new BookResultSetVisitor());

            if (books.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"未找到指定的图书\"}");
                return;
            }

            Book book = books.get(0);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try (Writer writer = response.getWriter()) {
                String json = this.objectMapper.writeValueAsString(book);
                writer.write(json);
                writer.flush();
            }
        } catch (SQLException e) {
            throw new ServletException("查询图书信息时发生错误", e);
        }
    }
}