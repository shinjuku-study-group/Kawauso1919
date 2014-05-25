/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.model;

import com.sun.javafx.binding.StringFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import minesweeper.util.Util;

/**
 * ゲームのタイマー。
 *
 * @author t-sato
 */
public class GameTimer {
    private boolean isRunning = false;
    private long timeLong;
    private final StringProperty timeStr = new SimpleStringProperty("");

    /**
     * 表示ラベルを受けるコンストラクタ。<br>
     * TODO ややキモイ？
     * 
     * @param time タイム表示ラベル 
     */
    public GameTimer(Label time) {
        time.textProperty().bind(timeStr);
    }

    /**
     * タイマーを止める。ついでにタイムを返す。
     * 
     * @return 終了時間。 
     */
    public String end() {
        isRunning = false;
        return timeStr.get();
    }

    /**
     * タイマー起動。
     */
    public void start() {
        timeLong = 0;
        timeStr.setValue("00:00:00");
        isRunning = true;

        new Thread(() -> {
            while (isRunning) {   
                Util.sleep(1000);
                if (!isRunning) {
                    break;
                }
                
                try {
                    Util.execFXAppThread(() -> {
                        timeLong++;
                        String fmStr = StringFormatter.format("%02d:%02d:%02d", timeLong / 3600, timeLong / 60, timeLong % 60).getValue();

                        timeStr.setValue(fmStr);
                        return null;
                    });
                } catch (Exception e) {
                    //TODO 失敗したらどうすればいいんだろうか。
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
