package cn.edu.swu.ws;

import cn.edu.swu.ws.db.BookResultSetVisitor;
import cn.edu.swu.ws.db.DatabaseService;
import cn.edu.swu.ws.db.ResulSetVisitor;
import cn.edu.swu.ws.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseService service = DatabaseService.getInstance();
        service.init();

        List<Book> books = service.query(
            "select * from book",
            new BookResultSetVisitor()
        );

        books.forEach(b -> {
            System.out.println(b.getAuthor());
        });

        List<String> names = service.query(
                "select name from book",
                new ResulSetVisitor(){
                    @Override
                    public List visit(ResultSet rs) throws SQLException {
                        List<String> names = new ArrayList<>();
                        while(rs.next()) {
                            names.add(rs.getString("name"));
                        }
                        return names;
                    }
                }
        );

        names.forEach(n -> {
            System.out.println(n);
        });
    }
}
