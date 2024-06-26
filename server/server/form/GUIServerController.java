/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import settings.Constants;
import settings.PropertiesLoader;
import start.Server;

import java.io.IOException;


public class GUIServerController {
    FXMLServerController fxmlServerController;
    private Server server;
    
    public GUIServerController(FXMLServerController fxmlServerController) {
        this.fxmlServerController = fxmlServerController;
        this.fxmlServerController.lblStatus.setText("Server is not listening");
        this.fxmlServerController.lblStatus.setTextFill(Color.color(1, 0, 0));
        this.fxmlServerController.btnStartServer.setOnAction(event -> onStartServer());
    }

    private void onStartServer() {
        fxmlServerController.btnStartServer.setOnMouseClicked((event) -> {
            if (server == null || !server.isAlive()) {
                    try {
                       server = new Server(Integer.parseInt(PropertiesLoader.getInstance().getProperty(Constants.PORT)));
                        server.start();

                        this.fxmlServerController.lblStatus.setText("Server is listening on port " + Integer.parseInt(PropertiesLoader.getInstance().getProperty(Constants.PORT)));
                        this.fxmlServerController.lblStatus.setTextFill(Color.color(0, 1, 0));
                        this.fxmlServerController.btnStartServer.setDisable(true);
                    } catch (IOException ex) {
                        showAlert("Port" + PropertiesLoader.getInstance().getProperty(Constants.PORT) + " is already taken.");
                    } 
                    
                } else {
                    showAlert("Server is already listening on this port.");
                }
        });
    }
    
    public void showAlert(String message) { 
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
