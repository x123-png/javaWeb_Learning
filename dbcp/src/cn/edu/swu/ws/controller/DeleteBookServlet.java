package cn.edu.swu.ws.controller;

import cn.edu.swu.ws.db.DatabaseService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/deleteBook")
public class DeleteBookServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String id = request.getParameter("id");
        String sql = "delete from book where id = " + id;

        ServletContext context = this.getServletContext();
        DatabaseService databaseService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        try {
            databaseService.execute(sql);
            response.sendRedirect("./books");
        } catch (IOException | SQLException e) {
            throw new ServletException(e);
        }
    }

}
