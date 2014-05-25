/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.util;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import javafx.application.Platform;

/**
 * ユーティリティ。
 *
 * @author t-sato
 */
public class Util {
    /**
     * 割り込み例外を捨てるスリープ。
     * 
     * @param millis ミリ秒
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * FXアプリケーションスレッドで処理させる。
     * 
     * @param callable コール内容
     * @return コールの戻り値
     */
    public static <V> V getFromApplicationThread(Callable<? extends V> callable) throws Exception {
        if (Platform.isFxApplicationThread()) {
            return callable.call();
        }
        RunnableFuture<V> future = new FutureTask(callable);
        Platform.runLater(future);
        return future.get();
    }
}
