/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;
import java.io.IOException;


public class FXMLServerController {
    
    @FXML
    public Label lblStatus;
    
    @FXML
    public Button btnStartServer;
    
    public GUIServerController guiServerController;
    
    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        guiServerController = new GUIServerController(this);      
    }    
}
