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

@WebServlet(urlPatterns = "/addBook")
public class AddBookServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String price = request.getParameter("price");
        String memo = request.getParameter("memo");
        String publish = request.getParameter("publish");

        ServletContext context = this.getServletContext();
        DatabaseService dbService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        String insertSQL = "insert into book (name, author, price, memo, publish) " +
                "values ('%s', '%s', %s, '%s', '%s')";

        try {
            dbService.execute(String.format(insertSQL, name, author, price, memo, publish));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("./books");
    }

}
