package cn.edu.swu;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JdbcTest {
    public static void main(String[] args) {
        JdbcTest test = new JdbcTest();
        String sql = "select id, name,author, price, memo, publish from where price < 40";
        List<Book> books = test.query(sql);
        test.printBooks(books);

        String insertSQL = "insert into book (name, author,price,memo,publish) values('%s','%s','%s','%s','%s')";

    }

    public List<Book> query(String sql) {
        String dbUrl = "jdbc:mysql://10.122.7.154:3306/book_store_2025";
        String dbUser = "root";
        String dbPass = "qewer1234";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    List<Book> books = new ArrayList<>();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String author = resultSet.getString("author");
                        String memo = resultSet.getString("memo");
                        double price = resultSet.getDouble(4);
                        Date publish = resultSet.getDate("publish");

                        Book book = new Book(id,name,author,price,memo,publish);
                        books.add(book);
                    }
                    return books;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printBooks(List<Book> books) {
        books.forEach(b ->{
            String strBook = String.format("%s,%s,%s,%s,%s,%s",
                    b.getId(),b.getName(),b.getAuthor(),b.getMemo(),b.getPrice(),b.getPublish());
            System.out.println(strBook);
        });
    }
}



















