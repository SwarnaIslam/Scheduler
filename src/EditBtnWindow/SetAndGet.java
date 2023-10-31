package EditBtnWindow;

public class SetAndGet {
    public static String tableName;
    public static String Query;

    public static String getTableName() {
        return tableName;
    }

    public static String getQuery() {
        return Query;
    }

    public static void setTableName(String tableName) {
        SetAndGet.tableName = tableName;
    }

    public static void setQuery(String query) {
        Query = query;
    }
}
