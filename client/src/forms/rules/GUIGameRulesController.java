package forms.rules;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import main.GameStage;


public class GUIGameRulesController {
    
    FXMLGameRulesController fxmlGameRulesController;

    public GUIGameRulesController(FXMLGameRulesController fxmlGameRulesController) {
        this.fxmlGameRulesController = fxmlGameRulesController;
        this.fxmlGameRulesController.btnBack.setOnAction(event -> onBack());
    }

    private void onBack() {
        fxmlGameRulesController.btnBack.setOnMouseClicked((event) -> {
            GameStage.getInstance().setScene("forms/main/FXMLMain.fxml");
        });
    }
    
    
}
