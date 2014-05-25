/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import minesweeper.control.GameMainController;
import minesweeper.model.GameConstant;
import minesweeper.model.GameMode;
import minesweeper.util.GameUtil;

/**
 *
 * @author t-sato
 */
public class Minesweeper extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMain.fxml"));
        Parent root = loader.load();
        
        GameMainController controller = (GameMainController) loader.getController();
        controller.setStage(stage);
        
        Scene scene = new Scene(root);
        stage.setTitle(GameConstant.TITLE());
        GameUtil.ajustStateSize(stage, GameMode.EASY);
       
        stage.setScene(scene);
        //TODO ✖ボタン押されてた時になんかうまく死んでくれない・・・
        stage.setOnCloseRequest((eh) -> {System.exit(0);});
        stage.setResizable(false);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
