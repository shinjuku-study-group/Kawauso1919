/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minesweeper.model;

/**
 * ゲームの難易度。
 * 
 * @author t-sato
 */
public enum GameMode {
    EASY(10, 6, 5),
    NORMAL(15, 10, 15),
    HARD(20, 16, 40),
    MANIA(30, 20, 120);
    //TODO CUSTOMとか？
    
    public final int column;
    public final int row;
    public final int mineNumber;
    
    private GameMode(final int column, final int row, final int mineNumber) {
        this.column = column;
        this.row = row;
        this.mineNumber = mineNumber;
    }
    
}