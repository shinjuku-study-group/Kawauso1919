/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * セル。<br>
 * なんかもう全部CellManager経由でやればいいんじゃないかな。
 *
 * @author t-sato
 */
public class MineCell {
    public int ix;
    public int iy;
    public int cellId;
    private CellType type = CellType.NORMAL;

    /**
     * セル文字列表現。
     */
    public StringProperty str = new SimpleStringProperty("");

    /**
     * セルの状態。
     */
    public CellState state = CellState.UNKNOWN;

    /**
     * セルマネージャ。
     */
    public final CellManager mgr;

    public MineCell(int ix, int iy, int cellId, CellManager mgr) {
        this.ix = ix;
        this.iy = iy;
        this.cellId = cellId;
        this.mgr = mgr;
    }
    
    public boolean hasMine() {
        return type.equals(CellType.MINE);
    }
    
    public void setMine() {
        type = CellType.MINE;
    }

    /**
     * 地雷処理。
     */
    public void sweep() {
        if (!CellState.UNKNOWN.equals(state)) {
            return;
        }

        switch (type) {
            case NORMAL:
                state = CellState.OPENED;
                mgr.soner(this);
                break;
            case MINE:
                mgr.explosion();
                break;
            default:
                break;
        }
    }
    
    /**
     * 周囲の地雷処理。
     */
    public void aroundSweep() {
        if (!CellState.OPENED.equals(state)) {
            return;
        }

        mgr.aroundSweep(this);
    }
    
    /**
     * フラグを立てる/取り除く。
     */
    public void flag() {
        mgr.flag(this);
    }
}
