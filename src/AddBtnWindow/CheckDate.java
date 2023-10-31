package AddBtnWindow;

import DatabaseControl.ConnectDatabase;
import DatabaseControl.DoesTableExist;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class CheckDate {
    public static void checkingDate(String dateString) throws DateException, SQLException {

        Connection con= ConnectDatabase.connection();
        LocalDate localDate=LocalDate.now();

        if(dateString.compareTo("")==0){
            con.close();
            throw  new DateException("You must set a date");
        }
        if (DoesTableExist.tableExistsSQL(con,dateString)){
            con.close();
            throw new DateException("This date already exists");
        }
        if(dateString.compareTo(localDate.toString())<0){
            con.close();
            throw new DateException("You can't set a previous date");
        }
    }
}
