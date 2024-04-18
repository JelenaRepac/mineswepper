package forms.user;

import forms.login.GUILoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FXMLUserController {

    @FXML
    public TextField txtname;

    @FXML
    public PasswordField txtPassword;
    @FXML
    public PasswordField txtRepeatPassword;
    @FXML
    public Button btnChange;
    @FXML
    public Button btnBack;

    @FXML
    public Label lblInfo;

    public GUIUserController guiUserController;

    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        guiUserController = new GUIUserController(this);
    }

}
