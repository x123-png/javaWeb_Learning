package cn.edu.swu.ws.book;

import cn.edu.swu.ws.book.Book;
import cn.edu.swu.ws.repo.ResultSetVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookResultSetVisitor implements ResultSetVisitor<Book> {
    @Override
    public List<Book> visit(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while(rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            book.setPrice(rs.getDouble("price"));
            book.setMemo(rs.getString("memo"));
            book.setPicture(rs.getString("picture"));
            book.setPublish(rs.getDate("publish"));
            books.add(book);
        }
        return books;
    }
}
