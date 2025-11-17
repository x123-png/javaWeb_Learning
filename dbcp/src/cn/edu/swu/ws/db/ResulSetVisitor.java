package cn.edu.swu.ws.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResulSetVisitor<T> {
    public List<T> visit(ResultSet rs) throws SQLException;
}
