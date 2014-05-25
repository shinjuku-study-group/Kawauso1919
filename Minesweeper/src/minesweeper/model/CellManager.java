/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import minesweeper.control.GameMainController;

/**
 * セルの管理および内部処理クラス。<br>
 * ちょっと色々やりすぎかもしれない。許して。
 *
 * @author t-sato
 */
public class CellManager {

    public final List<MineCell> cells;
    private final GameMode mode;
    private boolean first = true;
    private final GameMainController mainController;
    private boolean gameOver = false;

    public CellManager(GameMode mode, GameMainController mainController) {
        this.mode = mode;
        this.mainController = mainController;

        /* セルの初期化 */
        cells = new ArrayList<>();
        for (int iy = 0; iy < mode.row; iy++) {
            for (int ix = 0; ix < mode.column; ix++) {
                cells.add(new MineCell(ix, iy, iy * mode.column + ix, this));
            }
        }
    }

    /**
     * 周囲の地雷を検査し、状態を更新する。<br>
     *
     * @param org 基点のセル
     */
    public void soner(MineCell org) {
        // 初回のみ地雷の配置を行う。
        if (first) {
            createMine(org);
            first = false;
        }

        // 周囲の地雷数をカウント
        List<MineCell> aroundCells = getAroundCells(org);
        long count = aroundCells.stream().filter((mc) -> {
            return mc.hasMine();
        }).count();

        if (count == 0) {
            org.change(CellState.OPENED);

            // そのセルの周囲の地雷数が0なら、周囲のセルもオープンさせる。
            aroundCells.stream().forEach((mc) -> {
                mc.sweep();
            });
        } else {
            org.change(CellState.OPENED, Long.toString(count));
        }

        //終了判定
        if (!gameOver) {
            long cnt = cells.stream().filter((mc) -> {
                return !mc.hasMine();
            }).filter((mc) -> {
                return mc.state.equals(CellState.UNKNOWN);
            }).count();
            if (cnt == 0) {
                mainController.clear();
                gameOver = true;
            }
        }
    }

    /**
     * 爆弾配置。
     */
    private void createMine(MineCell org) {
        int mineCount = 0;
        while (mineCount < mode.mineCount) {
            int id = (int) (Math.random() * mode.column * mode.row);

            if (id != org.cellId && !cells.get(id).hasMine()) {
                cells.get(id).setMine();
                mineCount++;
                System.out.println("createMine: " + id);
            }
        }
    }

    /**
     * 基点セルの周囲3*3のセルを取得。
     */
    private List<MineCell> getAroundCells(MineCell org) {
        /* 範囲外のセルへのアクセス防止のバリア */
        final int six = org.ix - 1 < 0 ? 0 : org.ix - 1;
        final int eix = org.ix + 1 >= mode.column ? org.ix : org.ix + 1;
        final int siy = org.iy - 1 < 0 ? 0 : org.iy - 1;
        final int eiy = org.iy + 1 >= mode.row ? org.iy : org.iy + 1;

        final List<MineCell> arroundCells = new LinkedList<>();
        for (int iy = siy; iy <= eiy; iy++) {
            for (int ix = six; ix <= eix; ix++) {
                arroundCells.add(cells.get(iy * mode.column + ix));
            }
        }

        return arroundCells;
    }

    /**
     * やってしまった。
     */
    public void explosion() {
        gameOver = true;

        //旗を解除
        cells.stream().filter((mc) -> {
            return mc.state.equals(CellState.FLAG);
        }).forEach((mc) -> {
            mc.change(CellState.UNKNOWN);
        });

        cells.stream().forEach((mc) -> {
            if (mc.hasMine()) {
                mc.change(CellState.EXPLOSION);
            } else {
                mc.sweep();
            }
        });

        mainController.gameOver();
    }

    /**
     * 周囲の地雷処理。
     *
     * @param org 基点のセル
     */
    public void aroundSweep(MineCell org) {
        getAroundCells(org).stream().forEach((mc) -> {
            mc.sweep();
        });
    }

    /**
     * セルにフラグを立てる/取り除く。
     *
     * @param mc 対象のセル
     */
    public void flag(MineCell mc) {
        if (CellState.UNKNOWN.equals(mc.state)) {
            mc.change(CellState.FLAG);
            mainController.mineAdd(-1);
        } else if (CellState.FLAG.equals(mc.state)) {
            mc.change(CellState.UNKNOWN);
            mainController.mineAdd(1);
        }
    }
}
