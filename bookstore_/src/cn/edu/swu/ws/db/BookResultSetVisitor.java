package cn.edu.swu.ws.db;

import cn.edu.swu.ws.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookResultSetVisitor implements ResulSetVisitor<Book> {
    @Override
    public List<Book> visit(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while( rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            book.setPrice(rs.getDouble("price"));
            book.setMemo(rs.getString("memo"));
            book.setPublish(rs.getDate("publish"));
            books.add(book);
        }
        return books;
    }
}
