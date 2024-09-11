package util;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String SQL, Object... val) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL);

        for (int i = 0; i < val.length; i++) {
            preparedStatement.setObject(i+1, val[i]);
        }

        if (SQL.startsWith("SELECT") || SQL.startsWith("select")) {
            return (T) preparedStatement.executeQuery();
        }else{
            return (T) (Boolean) (preparedStatement.executeUpdate()>0);
        }
    }
}
