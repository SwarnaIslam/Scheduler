package AddTaskToSchedule;

import MainClass.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddTaskStage {
    public static void addTaskWindow() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/AddTaskToSchedule/AddTask.fxml"));
        AnchorPane anchorPane;
        anchorPane=fxmlLoader.load();

        Stage stage=new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.showAndWait();
    }
}
