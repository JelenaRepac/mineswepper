import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application{

    @Override
    public void start(Stage stage) {
        stage.setTitle("Mineswapper Server");
        stage.setResizable(false);
        ServerStage.setStage(stage);
        ServerStage.getInstance().setScene("form/FXMLServer.fxml");
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

}