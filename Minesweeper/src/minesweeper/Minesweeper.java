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
 * マインスイーパーのアプリケーション。
 * 
 * @author t-sato
 */
public class Minesweeper extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMain.fxml"));
        Parent root = loader.load();
        
        //メインコントーラにステージへの参照を持たせる。
        GameMainController controller = (GameMainController) loader.getController();
        controller.setStage(stage);
        
        //ステージの初期化処理
        stage.setTitle(GameConstant.TITLE());
        GameUtil.adjustStageSize(stage, GameMode.EASY);
        stage.setResizable(false);
       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        //TODO ✖ボタン押されてた時になんかうまく死んでくれないから強制即死させる。
        stage.setOnCloseRequest((eh) -> {System.exit(0);});
        
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
