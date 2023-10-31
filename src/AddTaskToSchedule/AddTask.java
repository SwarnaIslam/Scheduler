package AddTaskToSchedule;

import DatabaseControl.ConnectDatabase;
import DatabaseControl.CreateTable;
import DatabaseControl.DoesTableExist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AddTask {
    public static String tableName;
    public static Boolean doesExist=true;
    public static void addingTask(String tblName) throws IOException, SQLException {
        Connection con=ConnectDatabase.connection();
        if(!DoesTableExist.tableExistsSQL(con,tblName)){
            CreateTable.createTable(tblName);
            doesExist=false;
        }
        tableName=tblName;
        con.close();
        AddTaskStage.addTaskWindow();
    }

    public static String getTableName() {
        return tableName;
    }

    public static Boolean getDoesExist() {
        return doesExist;
    }

    public static void setDoesExist(Boolean doesExist) {
        AddTask.doesExist = doesExist;
    }
}
