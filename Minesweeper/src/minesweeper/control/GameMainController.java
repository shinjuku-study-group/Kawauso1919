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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import minesweeper.Minesweeper;
import minesweeper.model.CellManager;
import minesweeper.model.GameMode;
import minesweeper.model.GameTimer;
import minesweeper.util.GameUtil;
import minesweeper.view.MineCellLabel;

/**
 *
 * @author satout-
 */
public class GameMainController implements Initializable {

    private GameMode mode = GameMode.EASY;
    private CellManager cellManager;
    private GameTimer timer;

    @FXML
    private AnchorPane menuBar;
    @FXML
    private Label time;
    @FXML
    private Label mineCount;
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
    
    private Stage stage;
    
    public StringProperty mineCountProperty = new SimpleStringProperty("");
    public int mineCountInt = 0;

    @FXML
    private void startButtonAction(ActionEvent event) {
        gameOverMessage.setVisible(false);
        gameOverFilter.setVisible(false);
        gameClearMessage.setVisible(false);
        minePane.getChildren().clear();
        
        GameUtil.ajustStateSize(stage, mode);
        
        mineCountInt = 0;
        mineAdd(mode.mineNumber);
        
        cellManager = new CellManager(mode, this);
        cellManager.cells.stream().forEach((mc) -> {
            minePane.getChildren().add(new MineCellLabel(mc));
        });

        timer = new GameTimer(time);
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
        
        mineCount.textProperty().bind(mineCountProperty);
    }

    public void gameOver() {
        timer.end();
        gameOverMessage.setVisible(true);
        gameOverFilter.setVisible(true);
        GameUtil.playSE(Minesweeper.class.getResource("explosion.mp3"));
    }

    public void clear() {
        timer.end();
        gameClearMessage.setVisible(true);

        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.ZERO, (ActionEvent event) -> {
                    System.out.println("Key1");
                    BoxBlur blur = new BoxBlur(250, 1, 1);
                    gameClearMessage.setEffect(blur);
                }),
                new KeyFrame(Duration.millis(250), (ActionEvent event) -> {
                    System.out.println("Key1");
                    BoxBlur blur = new BoxBlur(200, 1, 1);
                    gameClearMessage.setEffect(blur);
                }),
                new KeyFrame(Duration.millis(250), (ActionEvent event) -> {
                    BoxBlur blur = new BoxBlur(140, 1, 1);
                    gameClearMessage.setEffect(blur);
                }),
                new KeyFrame(Duration.millis(250), (ActionEvent event) -> {
                    BoxBlur blur = new BoxBlur(80, 1, 1);
                    gameClearMessage.setEffect(blur);
                }),
                new KeyFrame(Duration.millis(250), (ActionEvent event) -> {
                    BoxBlur blur = new BoxBlur(20, 1, 1);
                    gameClearMessage.setEffect(blur);
                }),
                new KeyFrame(Duration.millis(250), (ActionEvent event) -> {
                    System.out.println("KeyEnd");
                    BoxBlur blur = new BoxBlur(0, 1, 1);
                    gameClearMessage.setEffect(blur);
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
        GameUtil.playSE(Minesweeper.class.getResource("Happy.mp3"));
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void mineAdd(int add) {
        mineCountInt += add;
        mineCountProperty.setValue(Integer.toString(mineCountInt));
    }
}
