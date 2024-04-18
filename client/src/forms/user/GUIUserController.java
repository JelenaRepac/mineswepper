package forms.user;

import communication.Controller;
import domain.Player;
import forms.login.FXMLLoginController;
import javafx.scene.control.Alert;
import main.GameStage;
import org.apache.commons.codec.digest.DigestUtils;

public class GUIUserController {


    FXMLUserController fxmlUserController;

    public GUIUserController(FXMLUserController fxmlUserController) {
        this.fxmlUserController = fxmlUserController;
        this.fxmlUserController.btnChange.setOnAction(event -> onChange());
        this.fxmlUserController.btnBack.setOnAction(event -> onBack());
        this.fxmlUserController.txtname.setText(client.session.Session.getInstance().getPlayer().getname());
    }


    private void onChange() {
        fxmlUserController.btnChange.setOnMouseClicked((event) -> {

            String name = fxmlUserController.txtname.getText();
            String password = DigestUtils.shaHex(fxmlUserController.txtPassword.getText());
            String rePassword = DigestUtils.shaHex(fxmlUserController.txtRepeatPassword.getText());

            if(!password.equals(rePassword)) {
                fxmlUserController.lblInfo.setText("Password mismatch");
            } else {
                try {
                    Player player = Controller.getInstance().changeCredentials(name, password);
                    client.session.Session.getInstance().setPlayer(player);
                    showMessageSuccessfully("Successfully changed credentials");
                } catch (RuntimeException re) {
                    re.getStackTrace();
                    showMessage(re.getMessage());
                } catch (Exception ex) {
                    ex.getStackTrace();
                    fxmlUserController.lblInfo.setText(ex.getMessage());
                }
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
    public void showMessageSuccessfully(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void onBack() {
        fxmlUserController.btnBack.setOnMouseClicked((event) -> {
            GameStage.getInstance().setScene("forms/main/FXMLMain.fxml");
        });
    }

}
