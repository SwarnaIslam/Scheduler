package MainClass;

import AddBtnWindow.AddStage;
import AddBtnWindow.ScheduleCancelling;
import AddBtnWindow.ScheduleConforming;
import AlarmControl.ControlAlarm;
import EditBtnWindow.EditStage;
import DatabaseControl.DeleteTable;
import DatabaseControl.DeletePastSchedule;
import DatabaseControl.RenameTable;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application{

    private static Stage primaryStage;
    private static BorderPane mainLayout;
    public static DeleteTable deleteTask;
    public static DeletePastSchedule deletePastSchedule;
    public static RenameTable renameTable;
    double x,y=0;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/SplashWindow/SplashWindow.fxml"));

        AnchorPane anchorpane;
        anchorpane=fxmlLoader.load();
        Scene popupScene = new Scene(anchorpane);
        Stage popupStage = new Stage();

        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.setScene(popupScene);
        popupStage.show();

        PauseTransition wait = new PauseTransition(Duration.seconds(1));
        wait.play();
        wait.setOnFinished((e) -> {
            popupStage.close();
            primaryStage=stage;
            primaryStage.setTitle("Schedule Tracker");

            try {
                deletePastSchedule.deletingTable();
                showMainView();
                homeView();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void showMainView() throws IOException {

        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/MainWindow/Main.fxml"));

        mainLayout=fxmlLoader.load();

        mainLayout.setOnMousePressed((e)->{
            x=e.getSceneX();
            y=e.getSceneY();
        });

        mainLayout.setOnMouseDragged((e)->{
            primaryStage.setX(e.getSceneX()-x);
            primaryStage.setY(e.getSceneY()-y);
        });
        Scene scene=new Scene(mainLayout,900,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void homeView() throws IOException {

        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/HomeBtnWindow/Home.fxml"));

        BorderPane mainItems;
        mainItems=fxmlLoader.load();

        mainLayout.setCenter(mainItems);
    }

    public static void allScheduleView()throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/AllTaskWindow/AllTask.fxml"));

        BorderPane allSchedule;
        allSchedule=fxmlLoader.load();

        mainLayout.setCenter(allSchedule);
    }

    public static EditStage editStage;
    public static void editBtnWindow() throws IOException {
        Stage allStage=new Stage();
        editStage.allStageShowing(primaryStage,allStage);
    }

    private static Stage addStage=new Stage();

    public static void showAddBtnWindow() throws IOException {
        AddStage.addStageShowing(primaryStage,addStage);
    }

    public static void scheduleConforming(String date) throws SQLException, IOException {
        ScheduleConforming.conformMethod(date,addStage);
    }
    public static void scheduleCancelling()throws IOException, SQLException {
        ScheduleCancelling.cancellingMethod(addStage);
    }
    public static void listShowing() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/ListWindow/List.fxml"));

        BorderPane listWindow;
        listWindow=fxmlLoader.load();

        mainLayout.setCenter(listWindow);
    }
    public static void showIncompleteList() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/IncompleteTask/IncompleteList.fxml"));

        BorderPane listWindow;
        listWindow=fxmlLoader.load();

        mainLayout.setCenter(listWindow);
    }
    public static void main(String[] args) throws IOException {
        launch(args);
        ControlAlarm.runAlarm();
    }
}
