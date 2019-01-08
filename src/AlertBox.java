import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(400);
        Label label = new Label();
        label.setText(message);
        Button button = new Button("OK");
        button.setOnAction(event -> stage.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void display(String message){
        display("Komuikat", message);
    }
}
