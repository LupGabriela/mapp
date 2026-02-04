package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUIMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = FXMLLoader.load(getClass().getResource("ProgramChooser.fxml"));
        Scene scene = new Scene(root, 1000, 511);
        stage.setScene(scene);
        stage.setTitle("Program Chooser");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
