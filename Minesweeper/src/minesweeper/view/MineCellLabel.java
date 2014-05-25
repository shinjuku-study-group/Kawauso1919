/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view;

import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import minesweeper.model.GameConstant;
import minesweeper.model.MineCell;

/**
 * ゲームのセル一つ一つのビュー。
 *
 * @author t-sato
 */
public class MineCellLabel extends Label {

    private final MineCell mc;

    public MineCellLabel(MineCell mc) {
        this.mc = mc;
        final int SIZE = GameConstant.CELL_SIZE();
        setLayoutX(mc.ix * SIZE + 3);
        setLayoutY(mc.iy * SIZE + 3);
        setMaxSize(SIZE, SIZE);
        setMinSize(SIZE, SIZE);
        setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: gray;");
        textProperty().bind(mc.str);
        setAlignment(Pos.CENTER);

        setOnMouseClicked((me) -> {
            switch (me.getButton()) {
                case PRIMARY:
                    if (me.isSecondaryButtonDown()) {
                        mc.aroundSweep();
                    } else {
                        mc.sweep();
                    }
                    break;
                case SECONDARY:
                    if (me.isPrimaryButtonDown()) {
                        mc.aroundSweep();
                    } else {
                        mc.flag();
                    }
                    break;
                default:
                    break;
            }
        });

        textProperty().addListener((Observable ob) -> {
            switch (mc.state) {
                case UNKNOWN:
                case OPENED:
                    if (getText().equals("　")) {
                        setStyle("-fx-background-color: gray; -fx-border-width: 1; -fx-border-color: gray;");
                    } else {
                        setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: gray;");
                    }
                    break;
                case FLAG:
                    setStyle("-fx-background-color: yellow; -fx-border-width: 1; -fx-border-color: gray;");
                    break;
                case EXPLOSION:
                    setStyle("-fx-background-color: red; -fx-border-width: 1; -fx-border-color: gray;");
                    break;
            }
        });

    }
}
