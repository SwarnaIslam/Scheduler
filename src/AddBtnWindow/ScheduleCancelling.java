package AddBtnWindow;

import DatabaseControl.DeleteTable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class ScheduleCancelling {
    public static void cancellingMethod(Stage addStage) throws SQLException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure to cancel the schedule?");
        ButtonType buttonTypeOne=new ButtonType("Yes");
        ButtonType buttonTypeTwo=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne,buttonTypeTwo);

        Optional<ButtonType> result=alert.showAndWait();

        if(result.get()==buttonTypeOne){
            DeleteTable.delete("Temp");
            addStage.close();
        }

    }
}
