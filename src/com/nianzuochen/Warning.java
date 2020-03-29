package com.nianzuochen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lei02 on 2019/4/23.
 * 呵呵
 * 功能很唯一，因为学校在 11：00 的时候会熄灯，电脑上的东西没有保存。
 * 需要提醒自己。
 * 开机自启动，当到达指定的事件之后显示一个窗体，提示保存内容，有图像和声音。
 */
public class Warning extends Application {
    private String time = "22:50:00";
    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private Calendar date;
    private Text text = new Text();//时间以文本形式显示
    private Timeline animation;
    private StackPane mainPane = new StackPane();
    private boolean change = false;

    @Override
    public void start(Stage primaryStage){
        String str = String.format("%02d:%02d:%02d", hour, minute, second);
        text.setText(str);
        text.setFont(Font.font(40));
        mainPane.getChildren().add(text);

        //铃声
        String musicPath = getClass().getResource("../../music/music.mp3").toString();
        Media media = new Media(musicPath);
        MediaPlayer gameback = new MediaPlayer(media);
        gameback.setVolume(30);

        //无限循环背景音乐
        gameback.setCycleCount(Timeline.INDEFINITE);

        //修改面板的颜色
        EventHandler<ActionEvent> changeColor = e -> {
            if (change) {
                text.setFill(Color.rgb(70, 96, 66));
                change = false;
            } else {
                text.setFill(Color.RED);
                change = true;
            }
        };
        Timeline changeAnimation = new Timeline(new KeyFrame(Duration.millis(500), changeColor));
        changeAnimation.setCycleCount(Timeline.INDEFINITE);

        //设置时间显示的动画
        EventHandler<ActionEvent> eventHandler = e -> {
            date = new GregorianCalendar();
            hour = date.get(Calendar.HOUR_OF_DAY);
            minute = date.get(Calendar.MINUTE);
            second = date.get(Calendar.SECOND);
            String datestr = String.format("%02d:%02d:%02d", hour, minute, second);
            text.setText(datestr);

            if (datestr.equals(time)) {
//                mainPane.setBackground(
//                        new Background(
//                                new BackgroundImage(new Image("image/background.jpg"), null, null, null, null)));
                primaryStage.setWidth(1000);
                primaryStage.setHeight(400);
                text.setText("快存储文件啊！！！");
                text.setFont(Font.font(60));
                animation.stop();
                gameback.play();
                changeAnimation.play();
            }
        };

        animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        Scene scene = new Scene(mainPane, 200, 100);
        primaryStage.setTitle("该关机了");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
