package forms.signup;/*
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


public class GUISignupController {
    FXMLSignupController fxmlSignupController;

    public GUISignupController(FXMLSignupController fxmlSignupController) {
        this.fxmlSignupController = fxmlSignupController;
        this.fxmlSignupController.btnSignup.setOnAction(event -> onSignup());
        this.fxmlSignupController.btnLogin.setOnAction(event -> onLogin());
    }
    
    private void onSignup() {
        String name = this.fxmlSignupController.txtname.getText();
        String password = DigestUtils.shaHex(this.fxmlSignupController.txtPassword.getText());
        String rePassword = DigestUtils.shaHex(this.fxmlSignupController.txtRePassword.getText());
            
        if(!password.equals(rePassword)) {
            this.fxmlSignupController.lblInfo.setText("Password mismatch");
        } else {
            try {
                Player player = Controller.getInstance().signup(name, password);
                player.setname(name);
                player.setPassword(password);
                Session.getInstance().setPlayer(player);
                GameStage.getInstance().setScene("forms/main/FXMLMain.fxml");
                
            } catch (RuntimeException re) {
                re.getStackTrace();
                showMessage(re.getMessage());
            } catch (Exception ex) {
                ex.getStackTrace();
                fxmlSignupController.lblInfo.setText(ex.getMessage());
            }   
        }     
    }    

    private void onLogin() {
        GameStage.getInstance().setScene("forms/login/FXMLLogin.fxml");
    }
    
    public void showMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText(message);
            alert.showAndWait();
    }
    
}
