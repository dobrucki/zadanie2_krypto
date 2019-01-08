import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public void start(Stage stage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        }
        catch (IOException e){
            System.out.println(e);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }
}
