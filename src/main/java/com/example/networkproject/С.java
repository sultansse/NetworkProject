package com.example.networkproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class С extends Application {

    private Stage w;
    String nn;
    int id;
    Scene Buttons, T, f;
    boolean ButtonFlag = false;

    private DataOutputStream toServer;
    private DataInputStream fromServer;


    private void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 55555);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
    }

    public Button kahootButton(String btnColor) {
        Button btn = new Button();
        btn.setMinWidth(295);
        btn.setMinHeight(295);
        btn.setStyle("-fx-background-color: " + btnColor);
        btn.setTextFill(Color.WHITE);
        btn.setPadding(new Insets(10, 10, 10, 10));
        btn.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        return btn;
    }

    public StackPane nicknamePane() {
        StackPane stackPane = new StackPane();
        TextField tf = new TextField();
        tf.setPromptText("Enter username");
        tf.setMaxWidth(200);
        tf.setMinHeight(40);
        tf.setAlignment(Pos.CENTER);
        Button btn = new Button("Enter");
        btn.setMaxWidth(200);
        btn.setMinHeight(40);
        btn.setStyle("-fx-background-color:#333333");
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 16));
        VBox vBox = new VBox(10);
        vBox.setMaxWidth(300);
        vBox.setMaxHeight(300);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(tf, btn);
        stackPane.getChildren().addAll(vBox);
        stackPane.setStyle("-fx-background-color: #46178f");

        btn.setOnAction(e -> {
            try {
                toServer.writeUTF(tf.getText());
                nn = tf.getText();
                w.setScene(Buttons);
                w.setTitle(tf.getText());
                id = fromServer.readInt();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return stackPane;
    }

    public StackPane pinPane() {
        StackPane stackPane = new StackPane();
        TextField tf = new TextField();
        tf.setPromptText("Game PIN");
        tf.setMaxWidth(200);
        tf.setMinHeight(40);
        tf.setAlignment(Pos.CENTER);
        Button btn = new Button("Enter");
        btn.setMaxWidth(200);
        btn.setMinHeight(40);
        btn.setStyle("-fx-background-color:#333333");
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 16));
        VBox vBox = new VBox(10);
        vBox.setMaxWidth(300);
        vBox.setMaxHeight(300);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(tf, btn);
        stackPane.getChildren().addAll(vBox);
        stackPane.setStyle("-fx-background-color: #3e147f");

        btn.setOnAction(e -> {
            try {
                toServer.writeInt(Integer.parseInt(tf.getText()));
                String status = fromServer.readUTF();
                if (status.equals("Success!")) {
                    w.setScene(new Scene(nicknamePane(), 600, 600));
                    w.setTitle("Enter Nickname");
                }
                System.out.println(status);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return stackPane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        w = primaryStage;

        Label l1 = new Label("Wrong");
        l1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 60));
        l1.setTextFill(Color.WHITE);
        l1.setAlignment(Pos.CENTER);
        BorderPane v1 = new BorderPane();
        v1.setCenter(l1);
        l1.setPadding(new Insets(10, 0, 0, 0));
        v1.setStyle("-fx-background-color: RED");

        Label l = new Label("OK");
        l.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 60));
        l.setTextFill(Color.WHITE);
        l.setAlignment(Pos.CENTER);
        BorderPane v = new BorderPane();
        l.setPadding(new Insets(10, 0, 0, 0));
        v.setCenter(l);
        v.setStyle("-fx-background-color: GREEN");

        connectToServer();

        StackPane stackPane = new StackPane();
        VBox vBox1 = new VBox(10);
        Button btnRed = kahootButton("red");
        Button btnOrange = kahootButton("orange");
        VBox vBox2 = new VBox(10);
        Button btnBlue = kahootButton("blue");
        Button btnGreen = kahootButton("green");
        vBox1.getChildren().addAll(btnRed, btnOrange);
        vBox2.getChildren().addAll(btnBlue, btnGreen);
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(vBox1, vBox2);
        stackPane.getChildren().addAll(hBox);

        btnBlue.setOnAction(e -> {
            try {
                System.out.println("btn Blue");
                toServer.writeInt(1);
                toServer.writeInt(id);
                if (fromServer.readInt() != 0) {
                    w.setScene(T);
                } else {
                    w.setScene(f);
                }
                ButtonFlag = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnOrange.setOnAction(e -> {
            try {
                System.out.println("btn Orange");
                toServer.writeInt(2);
                toServer.writeInt(id);
                if (fromServer.readInt() != 0) {
                    w.setScene(T);
                } else {
                    w.setScene(f);
                }
                ButtonFlag = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnRed.setOnAction(e -> {
            try {
                System.out.println("btn RED");
                toServer.writeInt(3);
                toServer.writeInt(id);
                if (fromServer.readInt() != 0) {
                    w.setScene(T);
                } else {
                    w.setScene(f);
                }
                ButtonFlag = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnGreen.setOnAction(e -> {
            try {
                System.out.println("btn Green");
                toServer.writeInt(4);
                toServer.writeInt(id);
                if (fromServer.readInt() != 0) {
                    w.setScene(T);
                } else {
                    w.setScene(f);
                }
                ButtonFlag = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        Timeline nt = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            if (ButtonFlag) {
                try {
                    if (123 == fromServer.readInt()) {
                        w.setScene(Buttons);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ButtonFlag = false;
            }
        }));


        nt.setCycleCount(Timeline.INDEFINITE);
        nt.play();
        Buttons = new Scene(stackPane, 600, 600);

        T = new Scene(new StackPane(v), 600, 600);
        f = new Scene(new StackPane(v1), 600, 600);

        Pane root = pinPane();
        w.setScene(new Scene(root, 600, 600));
        w.show();
        w.setMinHeight(600);
        w.setMinWidth(600);
        w.setResizable(false);
        w.setTitle("Enter PIN!");
        root.requestFocus();
    }
}