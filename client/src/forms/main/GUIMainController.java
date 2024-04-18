package forms.main;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import client.session.Session;


public class GUIMainController {
    
    FXMLMainController fxmlMainController;
    
    public GUIMainController(FXMLMainController fxmlMainController) {
        this.fxmlMainController = fxmlMainController;
        this.fxmlMainController.lblPlayer.setText(Session.getInstance().getPlayer().getname());
   //     this.fxmlMainController.mnItmPlayGame.setOnAction(event -> playGame());
    //    this.fxmlMainController.mnItmGameRules.setOnAction(event -> gameRules());

    }

//    private void playGame() {
//        fxmlMainController.mnItmPlayGame.setOnAction((event) -> {
//            try {
//                MinesweeperGameController controller;
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMain.fxml"));
//		GridPane rootGridPane = loader.load();
//		controller = loader.getController();
//	//	controller.createContent();
//                Scene scene = new Scene(rootGridPane);
//                GameStage.getInstance().setScene(scene);
//
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
//        });
//    }

//    private void gameRules() {
//        fxmlMainController.mnItmGameRules.setOnAction((event) -> {
//            GameStage.getInstance().setScene("forms/rules/FXMLGameRules.fxml");
//        });
//    }
}
