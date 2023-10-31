package AddBtnWindow;

import DatabaseControl.ConnectDatabase;
import DatabaseControl.RenameTable;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScheduleConforming {

    public static void conformMethod(String date, Stage addStage) throws IOException, SQLException {
        Connection con= ConnectDatabase.connection();
        PreparedStatement ps=null;
        try{
            String sql="INSERT INTO Date(days) VALUES(?)";
            ps=con.prepareStatement(sql);
            ps.setString(1,date);
            ps.execute();
            System.out.println("Data has been inserted!");
        }
        catch (SQLException e){
            System.out.println(e+"ScheduleConform");
        }
        finally {
            ps.close();
            con.close();
        }
        String tableName=date;
        RenameTable.rename(tableName);
        addStage.close();
    }
}
