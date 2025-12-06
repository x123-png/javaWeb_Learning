package cn.edu.swu.ws.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetVisitor<T> {
    public List<T> visit(ResultSet rs) throws SQLException;
}
