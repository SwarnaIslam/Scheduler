package AddBtnWindow;

import MainClass.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddStage {
    public static void addStageShowing(Stage primaryStage,Stage addStage)throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/AddBtnWindow/AddBtn.fxml"));

        if(addStage.getModality()== Modality.NONE){
            addStage.initModality(Modality.WINDOW_MODAL);
            addStage.initOwner(primaryStage);
            addStage.initStyle(StageStyle.UNDECORATED);
        }

        AnchorPane addDialogueBox;
        addDialogueBox=fxmlLoader.load();

        Scene scene=new Scene(addDialogueBox);

        addStage.setScene(scene);
        addStage.show();
    }
}
