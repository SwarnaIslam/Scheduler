package Search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class SearchController {
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXListView<String>listView;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private void searchAction(){
        String date;
        try {
            date=datePicker.getValue().toString();
            ShowFreeTime.showing(date,listView);
        }
        catch (NullPointerException | SQLException e){
            datePicker.setPromptText("Enter a date");
        }
    }
}
