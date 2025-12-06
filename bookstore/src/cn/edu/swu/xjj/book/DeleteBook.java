package cn.edu.swu.xjj.book;

import cn.edu.swu.xjj.repo.DatabaseService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/x123-png/deleteBook")
public class DeleteBook extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        ServletContext context = this.getServletContext();
        DatabaseService dbService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        String deleteSQL = "delete from book where id=" + id;

        try {
            dbService.execute(deleteSQL);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("./books");
    }

}
