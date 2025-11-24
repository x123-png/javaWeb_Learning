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

@WebServlet(urlPatterns = "/books")
public class BookServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServletContext context = request.getServletContext();
        DatabaseService service = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);

        System.out.println("DatabaseService: "+service); //调试输出

        if(service == null) {
            try{
                response.getWriter().write("<h1>错误：数据库服务未初始化</h1>");
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            List<Book> books = service.query(
                    "select * from book", new BookResultSetVisitor());

            System.out.println("The number of books queried is："+books.size());;

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
