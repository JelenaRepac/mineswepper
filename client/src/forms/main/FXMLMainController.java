package forms.main;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.GameStage;


public class FXMLMainController {
    
    @FXML
    public MenuBar menuBarMainMenu;
    
    @FXML
    public MenuItem mnItmPlayGame;

    @FXML
    public MenuItem mnItmGameRules;
    
    @FXML
    public Label lblWelcome;
    
    @FXML
    public Label lblPlayer;
    public GUIMainController guiMainController;
    
    @FXML
    private void playGame(ActionEvent event) throws IOException {
        GameStage.getInstance().setScene("forms/game/FXMLGame.fxml");
    }
    
    @FXML
    private void gameRules(ActionEvent event) {
        GameStage.getInstance().setScene("forms/rules/FXMLGameRules.fxml");
    }


    @FXML
    private void user(ActionEvent event){
        GameStage.getInstance().setScene("forms/user/FXMLUser.fxml");
    }
    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        guiMainController = new GUIMainController(this);

    }
}
