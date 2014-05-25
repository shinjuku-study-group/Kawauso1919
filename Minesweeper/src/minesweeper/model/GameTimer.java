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
 * ゲームのタイマー。<br>
 * これコントロールか？モデルか？分からん・・
 *
 * @author t-sato
 */
public class GameTimer {

    private boolean isRunning = false;
    private long timeLong;
    private final StringProperty timeStr = new SimpleStringProperty("");

    public GameTimer(Label time) {
        time.textProperty().bind(timeStr);
    }

    public String end() {
        isRunning = false;
        return timeStr.get();
    }

    public void start() {
        timeLong = 0;
        timeStr.setValue("00:00:00");
        isRunning = true;

        new Thread(() -> {
            while (isRunning) {   
                Util.sleep(1000);
                
                try {
                    Util.getFromApplicationThread(() -> {
                        timeLong++;
                        String fmStr = StringFormatter.format("%02d:%02d:%02d", timeLong / 3600, timeLong / 60, timeLong % 60).getValue();

                        timeStr.setValue(fmStr);
                        return null;
                    });
                } catch (Exception e) {

                }
            }
        }).start();
    }
}
