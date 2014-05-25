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
    DEBUG(20, 15, 1),
    EASY(10, 6, 5),
    NORMAL(18, 12, 32),
    HARD(24, 18, 75),
    MANIA(32, 22, 150),
    CRAZY(46, 25, 300);
    //TODO CUSTOMとか？
    
    public final int column;
    public final int row;
    public final int mineCount;
    
    private GameMode(final int column, final int row, final int mineCount) {
        this.column = column;
        this.row = row;
        this.mineCount = mineCount;
    }
}
