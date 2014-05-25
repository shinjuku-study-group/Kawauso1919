/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import minesweeper.Minesweeper;
import minesweeper.model.CellManager;
import minesweeper.model.GameMode;
import minesweeper.model.GameTimer;
import minesweeper.util.GameUtil;
import minesweeper.view.MineCellLabel;

/**
 * メインのコントローラ。<br>
 * 
 * @author t-sato
 */
public class GameMainController implements Initializable {

    private GameMode mode = GameMode.EASY;
    private CellManager cellManager;
    private GameTimer timer;
    private Stage stage;
    public StringProperty mineCountProperty = new SimpleStringProperty("");
    public int mineCountInt = 0;

    @FXML
    private Label timeLabel;
    @FXML
    private Label mineCountLabel;
    @FXML
    private Pane minePane;
    @FXML
    private ChoiceBox modeChoice;
    @FXML
    private Label gameOverMessage;
    @FXML
    private AnchorPane gameOverFilter;
    @FXML
    private Label gameClearMessage;

    @FXML
    private void startButtonAction(ActionEvent event) {
        gameOverMessage.setVisible(false);
        gameOverFilter.setVisible(false);
        gameClearMessage.setVisible(false);

        GameUtil.adjustStageSize(stage, mode);

        mineCountInt = 0;
        mineAdd(mode.mineCount);

        minePane.getChildren().clear();
        cellManager = new CellManager(mode, this);
        cellManager.cells.stream().forEach((mc) -> {
            minePane.getChildren().add(new MineCellLabel(mc));
        });

        timer = new GameTimer(timeLabel);
        timer.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO ChoiceBoxの使い方がわからん・・・
        modeChoice.setItems(FXCollections.observableArrayList(GameMode.values()));
        modeChoice.getSelectionModel().select(mode);
        modeChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GameMode>() {
            @Override
            public void changed(ObservableValue<? extends GameMode> observable, GameMode oldValue, GameMode newValue) {
                mode = newValue;
            }
        });
        mineCountLabel.textProperty().bind(mineCountProperty);
    }

    public void gameOver() {
        timer.end();
        gameOverMessage.setVisible(true);
        gameOverFilter.setVisible(true);
        GameUtil.playSE(Minesweeper.class.getResource("explosion.mp3"));
    }

    /**
     * ゲームクリア。
     */
    public void clear() {
        timer.end();
        gameClearMessage.setVisible(true);

        execGameClearAnimation();
        
        GameUtil.playSE(Minesweeper.class.getResource("Happy.mp3"));
        
        //TODO 記録
    }

    private void execGameClearAnimation() {
        //TODO 滅茶苦茶
        KeyFrame[] keyFrames = createKeyFrames(8, 20, 13);
        keyFrames[keyFrames.length - 1] = new KeyFrame(Duration.millis(8 * 15), (ActionEvent event) -> {
            Lighting lighting = new Lighting();
            lighting.setDiffuseConstant(1.5);
            lighting.setSurfaceScale(3.8);
            lighting.setSpecularExponent(25.15);
            lighting.setSpecularConstant(1.0);
            Light light = new Light.Distant(40, 50, Color.WHITE);
            lighting.setLight(light);
            gameClearMessage.setEffect(lighting);
        });
        Timeline timeline = new Timeline(keyFrames);
        timeline.setCycleCount(1);
        timeline.play();
    }

    private KeyFrame[] createKeyFrames(long duration, int bulurValueStep, int frameSize) {
        KeyFrame[] result = new KeyFrame[frameSize+1];
        for (int i = 0; i < frameSize; i++) {
            result[i] = createKeyFrame(duration * i, 255 - bulurValueStep * i < 0 ? 0: 255 - bulurValueStep * i);
        }
        return result;
    }

    private KeyFrame createKeyFrame(long duration, int bulurValue) {
        return new KeyFrame(Duration.millis(duration), (ActionEvent event) -> {
            BoxBlur blur = new BoxBlur(bulurValue, 1, 1);
            gameClearMessage.setEffect(blur);
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void mineAdd(int add) {
        mineCountInt += add;
        mineCountProperty.setValue(Integer.toString(mineCountInt));
    }
}
