/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.util;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import minesweeper.model.GameConstant;
import minesweeper.model.GameMode;

/**
 * ゲーム寄りのユーティリティ。
 *
 * @author t-sato
 */
public class GameUtil {

    public static void ajustStateSize(Stage stage, GameMode mode) {
        stage.setMinWidth(mode.column * GameConstant.CELL_SIZE() + GameConstant.STAGE_W_MARGIN());
        stage.setMinHeight(mode.row * GameConstant.CELL_SIZE() + GameConstant.STAGE_H_MARGIN());
        stage.setMaxWidth(mode.column * GameConstant.CELL_SIZE() + GameConstant.STAGE_W_MARGIN());
        stage.setMaxHeight(mode.row * GameConstant.CELL_SIZE() + GameConstant.STAGE_H_MARGIN());
    }

    public static void playSE(URL url) {
        Media audioMedia = new Media(url.toString());
        MediaPlayer audioPlayer = new MediaPlayer(audioMedia);
        audioPlayer.play();
    }
}
