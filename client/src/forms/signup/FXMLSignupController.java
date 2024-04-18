package forms.signup;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLSignupController{
    
    @FXML
    public Label lblname;
    
    @FXML
    public TextField txtname;

    @FXML
    public Label lblPassword;
    
    @FXML
    public PasswordField txtPassword;
    
    @FXML
    public Label lblRePassword;
    
    @FXML
    public PasswordField txtRePassword;
    
    @FXML
    public Label lblInfo;
    
    @FXML
    public Button btnSignup;
    
    @FXML
    public Button btnLogin;

    public GUISignupController guiSignupController;
    
    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        guiSignupController = new GUISignupController(this);      
    }    
}
