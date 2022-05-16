package com.example.networkproject;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;


public class S extends Application {

    boolean play = false;

    int AnsCount = 0;
    Scene s1;
    Scene s2;
    Scene s3;
    Scene s4;
    Scene s6;

    public Button But(String text, String color) {
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
        Button button = new Button(text);
        button.setMinSize(300, 100);
        button.setStyle("-fx-background-color: " + color);
        button.setTextFill(Color.WHITE);
        button.setFont(font);
        return button;
    }

    public static void main(String[] args) {
        launch();
    }

    int pin = ((int) (Math.random() * 899999) + 100000);
    Stage W;

    @Override
    public void start(Stage primaryStage) throws Exception {
        W = primaryStage;
        StackPane pane = new StackPane();
        final int[] cout = {0};
        final int[] pl = {0};
        final Boolean[] nq = {false};
        final int[] max = {0};

        FileChooser f = new FileChooser();
        Quiz q = new Quiz();
        Button next = new Button("Choose your TXT file");
        next.setFont(Font.font("Arial", 50));

        next.setOnAction(e -> {
            File fl = f.showOpenDialog(primaryStage);
            if (fl.getName().matches(".*txt*.")) {
                play = true;
                q.loader(fl);
                W.setScene(s2);
            }
        });

        pane.setStyle("-fx-background-color: #660099");
        pane.getChildren().addAll(next);
        s1 = new Scene(pane, 1000, 600);


        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #660099");


        HBox names = new HBox(10);
        VBox set = new VBox(200);
        BorderPane borderPane = new BorderPane();
        set.setAlignment(Pos.CENTER);
        Label lbl = new Label("PIN : " + pin);
        lbl.setPadding(new Insets(20));
        lbl.setTextFill(Color.WHITE);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        lbl.setAlignment(Pos.CENTER);
        lbl.setMinWidth(1000);
        borderPane.setTop(lbl);
        borderPane.setCenter(set);
        names.setAlignment(Pos.CENTER);
        root.getChildren().addAll(borderPane);
        Button start = new Button("Start");
        start.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
        start.setAlignment(Pos.CENTER);
        set.getChildren().addAll(names, start);

        int[] score = new int[10];
        String[] name = new String[10];

        StackPane tik = new StackPane();
        Circle c = new Circle();
        c.setRadius(50);
        c.setFill(Color.PURPLE);
        Label time = new Label("30");
        time.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        time.setTextFill(Color.WHITE);
        tik.setPadding(new Insets(10));
        tik.getChildren().addAll(c, time);


        Label dr = new Label();
        dr.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        dr.setPadding(new Insets(0, 0, 50, 0));
        BorderPane P1 = new BorderPane();
        Button B1 = But("", "BLUE");
        Button B2 = But("", "ORANGE");
        Button B3 = But("", "RED");
        Button B4 = But("", "GREEN");

        Timeline tim = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {

            dr.setText(q.Store2(cout[0]));
            B1.setText(q.Store1(cout[0], 0));
            B2.setText(q.Store1(cout[0], 1));
            B3.setText(q.Store1(cout[0], 2));
            B4.setText(q.Store1(cout[0], 3));

        }));
        tim.setCycleCount(Timeline.INDEFINITE);
        tim.play();

        VBox v1 = new VBox(2, B1, B2);
        VBox v2 = new VBox(2, B3, B4);
        HBox H = new HBox(2, v1, v2);
        H.setPadding(new Insets(0, 0, 0, 200));

        Button nextP1 = new Button("NEXT");
        nextP1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        nextP1.setTextFill(Color.WHITE);
        nextP1.setAlignment(Pos.CENTER_LEFT);
        nextP1.setPrefSize(120, 40);
        nextP1.setStyle("-fx-background-color: " + "PURPLE");
        nextP1.setOnAction(actionEvent -> {
            time.setText("30");
            AnsCount = 0;
            nq[0] = true;
            if (q.f[cout[0]].equals("true")) {
                W.setScene(s3);
            } else W.setScene(s4);
        });

        Button nextP = new Button();
        nextP.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 30));
        nextP.setTextFill(Color.YELLOW);
        nextP.setAlignment(Pos.CENTER_LEFT);
        nextP.setPrefSize(120, 40);
        nextP.setStyle("-fx-background-color: " + "PURPLE");

        nextP.setOnAction(actionEvent -> {
            cout[0]++;
            W.setScene(s6);
            if (cout[0] == max[0]) {
                nextP1.setVisible(false);
            }
        });

        Color[] col = new Color[10];
        for (int i = 0; i < 10; i++) {
            col[i] = Color.rgb(((int) (Math.random() * 255)), ((int) (Math.random() * 255)), ((int) (Math.random() * 255)));
        }

        StackPane[] p = new StackPane[10];
        p[0] = new StackPane();
        Label ln1 = new Label(name[0] + "          " + score[0]);
        ln1.setTextFill(Color.WHITE);
        ln1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        Rectangle r1 = new Rectangle(600, 50);
        r1.setFill(col[0]);
        p[0].getChildren().addAll(r1, ln1);

        p[1] = new StackPane();
        Label ln2 = new Label(name[1] + "          " + score[1]);
        ln2.setTextFill(Color.WHITE);
        ln2.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        Rectangle r2 = new Rectangle(600, 50);
        r2.setFill(col[1]);
        p[1].getChildren().addAll(r2, ln2);

        p[2] = new StackPane();
        Label ln3 = new Label(name[2] + "          " + score[2]);
        ln3.setTextFill(Color.WHITE);
        ln3.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        Rectangle r3 = new Rectangle(600, 50);
        r3.setFill(col[2]);
        p[2].getChildren().addAll(r3, ln3);

        p[3] = new StackPane();
        Label ln4 = new Label(name[3] + "          " + score[3]);
        ln4.setTextFill(Color.WHITE);
        ln4.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        Rectangle r4 = new Rectangle(600, 50);
        r4.setFill(col[3]);
        p[3].getChildren().addAll(r4, ln4);

        p[4] = new StackPane();
        Label ln5 = new Label(name[4] + "          " + score[4]);
        ln5.setTextFill(Color.WHITE);
        ln5.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        Rectangle r5 = new Rectangle(600, 50);
        r5.setFill(col[4]);
        p[4].getChildren().addAll(r5, ln5);

        Label AnsC = new Label("Answers: " + AnsCount);

        VBox Hlist = new VBox(10);
        BorderPane l = new BorderPane();
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            AnsC.setText("Answers: " + AnsCount);

            ln1.setText(name[0] + "          " + score[0]);
            ln2.setText(name[1] + "          " + score[1]);
            ln3.setText(name[2] + "          " + score[2]);
            ln4.setText(name[3] + "          " + score[3]);
            ln5.setText(name[4] + "          " + score[4]);

            if (Integer.parseInt(time.getText()) > 0 && AnsCount != pl[0]) {
                nextP.setText("Skip");
                B2.setVisible(true);
                B1.setVisible(true);
                B4.setVisible(true);
                B3.setVisible(true);
                B1.setStyle("-fx-background-color: BLUE");
                B2.setStyle("-fx-background-color: ORANGE");
                B3.setStyle("-fx-background-color: RED");
                B4.setStyle("-fx-background-color: GREEN");
                time.setText(String.valueOf(Integer.parseInt(time.getText()) - 1));
            } else {
                nextP.setText("Next");
                if (B1.getText().equals(q.answer(cout[0]))) {
                    B1.setStyle("-fx-background-color: GREEN");
                    B2.setVisible(false);
                    B3.setVisible(false);
                    B4.setVisible(false);
                } else if (B2.getText().equals(q.answer(cout[0]))) {
                    B2.setStyle("-fx-background-color: GREEN");
                    B1.setVisible(false);
                    B3.setVisible(false);
                    B4.setVisible(false);
                } else if (B3.getText().equals(q.answer(cout[0]))) {
                    B3.setStyle("-fx-background-color: GREEN");
                    B2.setVisible(false);
                    B1.setVisible(false);
                    B4.setVisible(false);
                } else if (B4.getText().equals(q.answer(cout[0]))) {
                    B4.setStyle("-fx-background-color: GREEN");
                    B2.setVisible(false);
                    B3.setVisible(false);
                    B1.setVisible(false);
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);

        Image kahoot = new Image(new FileInputStream("D:\\Изображение\\обои наруто\\oo.png"));
        ImageView logo = new ImageView(kahoot);
        logo.setFitWidth(400);
        logo.setFitHeight(250);
        VBox v = new VBox(dr, logo);
        v.setPadding(new Insets(0, 0, 0, 0));
        v.setAlignment(Pos.CENTER);

        P1.setBottom(H);
        P1.setCenter(v);
        P1.setLeft(tik);

        AnsC.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 15));
        VBox AnsBox = new VBox(10, nextP, AnsC);

        P1.setRight(AnsBox);
        start.setOnAction(actionEvent -> {
            W.setScene(s3);

            try {
                q.start();
                max[0] = q.max();
                timer.play();
                for (int i = 0; i < pl[0]; i++) {
                    Hlist.getChildren().add(p[i]);
                    Hlist.setAlignment(Pos.CENTER);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        l.setRight(nextP1);
        l.setCenter(Hlist);
        s6 = new Scene(l, 1000, 600);

        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(55555);
                while (true) {
                    try {
                        Socket socket = server.accept();
                        new Thread(() -> {
                            try {
                                DataInputStream from = new DataInputStream(socket.getInputStream());
                                DataOutputStream to = new DataOutputStream(socket.getOutputStream());
                                while (true) {
                                    int clientPin = from.readInt();
                                    if (clientPin != pin) {
                                        to.writeUTF("Wrong PIN!");
                                    } else {
                                        to.writeUTF("Success!");
                                    }
                                    String nickname = from.readUTF();
                                    System.out.println(nickname);
                                    name[pl[0]] = nickname;
                                    to.writeInt(pl[0]);
                                    pl[0]++;
                                    System.out.println(pl[0]);
                                    Button nick = new Button(nickname);
                                    nick.setTextFill(Color.BLACK);
                                    nick.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
                                    Platform.runLater(() -> names.getChildren().add(nick)
                                    );
                                    while (true) {
                                        int[] J = new int[10];
                                        for (int i = 0; i < 10; i++) {
                                            J[i] = 0;
                                        }
                                        int clientChoice = from.readInt();
                                        int clientID = from.readInt();
                                        if (clientChoice == 1) {
                                            if (Objects.equals(B1.getText(), q.answer(cout[0]))) {
                                                J[clientID] = (int) (100.0 * (Double.parseDouble(time.getText()) / 30.0));
                                                score[clientID] += J[clientID];
                                            }
                                        } else if (clientChoice == 2) {
                                            if (Objects.equals(B2.getText(), q.answer(cout[0]))) {
                                                J[clientID] = (int) (100.0 * (Double.parseDouble(time.getText()) / 30.0));
                                                score[clientID] += J[clientID];
                                            }
                                        } else if (clientChoice == 3) {
                                            if (Objects.equals(B3.getText(), q.answer(cout[0]))) {
                                                J[clientID] = (int) (100.0 * (Double.parseDouble(time.getText()) / 30.0));
                                                score[clientID] += J[clientID];
                                            }
                                        } else if (clientChoice == 4) {
                                            if (Objects.equals(B4.getText(), q.answer(cout[0]))) {
                                                J[clientID] = (int) (100.0 * (Double.parseDouble(time.getText()) / 30.0));
                                                score[clientID] += J[clientID];
                                            }
                                        }
                                        //
                                        to.writeInt(J[clientID]);
                                        System.out.println(score[clientID]);
                                        AnsCount++;
                                        System.out.println(clientChoice);
                                        while (true) {
                                            if (nq[0]) {
                                                nq[0] = false;
                                                System.out.println("sending to client");
                                                to.writeInt(123);
                                                System.out.println("sent to ClientSide");
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        s2 = new Scene(root, 1000, 600);
        s3 = new Scene(P1, 1000, 600);
        W.setMinWidth(1000);
        W.setMaxWidth(1000);
        W.setMinHeight(600);
        W.setMaxHeight(600);
        W.setScene(s1);
        W.setTitle("Server");
        W.show();

    }
}
