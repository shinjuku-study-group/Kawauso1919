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
    /**
     * ステージサイズを難易度に合わせて調整する。
     * 
     * @param stage ステージ
     * @param mode 難易度
     */
    public static void adjustStageSize(Stage stage, GameMode mode) {
        stage.setMinWidth(mode.column * GameConstant.CELL_SIZE() + GameConstant.STAGE_W_MARGIN());
        stage.setMinHeight(mode.row * GameConstant.CELL_SIZE() + GameConstant.STAGE_H_MARGIN());
        stage.setMaxWidth(mode.column * GameConstant.CELL_SIZE() + GameConstant.STAGE_W_MARGIN());
        stage.setMaxHeight(mode.row * GameConstant.CELL_SIZE() + GameConstant.STAGE_H_MARGIN());
    }

    /**
     * 音を鳴らす。超雑。めんどい。
     * 
     * @param url メディアのURL
     */
    public static void playSE(URL url) {
        //TODO サウンドUtilとかかな。
        Media audioMedia = new Media(url.toString());
        MediaPlayer audioPlayer = new MediaPlayer(audioMedia);
        audioPlayer.play();
    }
}
