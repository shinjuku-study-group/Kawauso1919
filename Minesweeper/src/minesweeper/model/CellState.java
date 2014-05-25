/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.model;

/**
 * セルの状態。
 *
 * @author t-sato
 */
public enum CellState {
    UNKNOWN(""),
    OPENED("　"),
    FLAG("旗"),
    EXPLOSION("爆");

    public final String stringValue;

    private CellState(String stringValue) {
        this.stringValue = stringValue;
    }
}
