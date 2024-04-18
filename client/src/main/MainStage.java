package main;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.stage.Stage;


public class MainStage extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mineswepper");
        stage.setResizable(false);
        GameStage.setStage(stage);
        GameStage.getInstance().setScene("forms/login/FXMLLogin.fxml");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
