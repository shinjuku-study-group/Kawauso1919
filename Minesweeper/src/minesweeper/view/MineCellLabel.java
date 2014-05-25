/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view;

import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import minesweeper.model.CellState;
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
        //TODO CSSでやればいいんじゃない？
        setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: gray;");
        textProperty().bind(mc.str);
        setAlignment(Pos.CENTER);

        //マウスクリックのリスナ登録
        addMouceClickListener();

        //テキストプロパティ変更のリスナ登録
        addTextChangeListener();
    }

    /**
     * マウスクリックのリスナ登録
     */
    private void addMouceClickListener() {
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
    }

    /**
     * テキストプロパティ変更のリスナ登録
     */
    private void addTextChangeListener() {
        textProperty().addListener((Observable ob) -> {
            switch (mc.state) {
                case UNKNOWN:
                case OPENED:
                    //TODO この判定キモイ。状態を分けるべきかも。
                    if (getText().equals(CellState.OPENED.stringValue)) {
                        //安全地帯(周囲の地雷なし)=>グレーアウト
                        setStyle("-fx-background-color: #CCCCCC; -fx-border-width: 1; -fx-border-color: gray;");
                    } else {
                        //周囲に地雷あり=>数字の表示
                        setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: gray;");
                        //TODO 数字に色付けたいなあ・・
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
