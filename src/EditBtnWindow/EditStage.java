package EditBtnWindow;

import MainClass.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EditStage {
    public static void allStageShowing(Stage primaryStage,Stage allStage) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/EditBtnWindow/EditBtn.fxml"));

        if(allStage.getModality()== Modality.NONE){
            allStage.initModality(Modality.WINDOW_MODAL);
            allStage.initOwner(primaryStage);
            allStage.initStyle(StageStyle.UTILITY);
        }
        BorderPane editPane;
        editPane=fxmlLoader.load();
        Scene scene=new Scene(editPane);

        allStage.setScene(scene);
        allStage.show();

    }
}
