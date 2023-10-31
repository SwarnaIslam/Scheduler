package DatabaseControl;

import java.sql.*;

public class DoesTableExist {
    public static boolean tableExistsSQL(Connection connection, String tableName) throws SQLException {
        char[] dateChar = tableName.toCharArray();
        dateChar[4] = '_';
        dateChar[7] = '_';
        tableName = String.valueOf(dateChar);
        tableName = "d" + tableName;
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }
}
