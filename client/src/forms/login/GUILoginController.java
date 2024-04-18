package forms.login;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import client.session.Session;
import communication.Controller;
import domain.Player;
import javafx.scene.control.Alert;
import main.GameStage;
import org.apache.commons.codec.digest.DigestUtils;

public class GUILoginController {
    FXMLLoginController fxmlLoginController;

    public GUILoginController(FXMLLoginController fxmlLoginController) {
        this.fxmlLoginController = fxmlLoginController;
        this.fxmlLoginController.btnLogin.setOnAction(event -> onLogin());
        this.fxmlLoginController.btnSignup.setOnAction(event -> onSignup());
    }
    
    private void onSignup() {
        GameStage.getInstance().setScene("forms/signup/FXMLSignup.fxml");
    }    

    private void onLogin() {
        fxmlLoginController.btnLogin.setOnMouseClicked((event) -> {
            String name = fxmlLoginController.txtname.getText();
            String password = DigestUtils.shaHex(fxmlLoginController.txtPassword.getText());
            try {
                Player player = Controller.getInstance().login(name, password);
                Session.getInstance().setPlayer(player);
                GameStage.getInstance().setScene("forms/main/FXMLMain.fxml");
                 
            } catch (RuntimeException re) {
                re.getStackTrace();
                showMessage(re.getMessage());
            } catch (Exception ex) {
                ex.getStackTrace();
                fxmlLoginController.lblInfo.setText(ex.getMessage());
            }
        });
    }
    
    public void showMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText(message);
            alert.showAndWait();
    }
}
