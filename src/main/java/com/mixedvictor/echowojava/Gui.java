// Copyright (C) 2022  MixedVictor
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

package com.mixedvictor.echowojava;   

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;

 
public class Gui extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("echowoJava");
        primaryStage.setResizable(false);

        VBox rootVbox = new VBox(7);
        HBox rootHbox = new HBox(7);
        Clipboard clip = Clipboard.getSystemClipboard();
        ClipboardContent clipContent = new ClipboardContent();
        TextArea inputArea = new TextArea();
        TextArea outputArea = new TextArea();
        Button refButton = new Button();
        Button copyButton = new Button();

        Gson gson = new Gson();
        URL fileJson = Cli.class.getClassLoader().getResource(Words.JSON_PATH);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(fileJson.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Words words = gson.fromJson(br, Words.class);        

        inputArea.setText("Put something here");
        inputArea.setWrapText(true);
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        rootHbox.setAlignment(Pos.CENTER);
        refButton.setText("UwUify");
        copyButton.setText("Copy");

        refButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                outputArea.setText((words.uwusOut(inputArea.getText())));
            }
        });

        copyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clipContent.putString(outputArea.getText());
                clip.setContent(clipContent);
            }
        });
        
        rootVbox.getChildren().add(inputArea);
        VBox.setMargin(inputArea, new Insets(5, 5, 1, 5));

        rootVbox.getChildren().addAll(rootHbox);
        rootHbox.getChildren().addAll(refButton, copyButton);
        HBox.setMargin(refButton, new Insets(1, 1, 1, 5));
        HBox.setMargin(copyButton, new Insets(1, 1, 1, 5));

        rootVbox.getChildren().add(outputArea);
        VBox.setMargin(outputArea, new Insets(1, 5, 5, 5));
        
        Scene scene = new Scene(rootVbox, 426, 240);

        URL fileCss = Cli.class.getClassLoader().getResource(Words.CSS_PATH);
        scene.getStylesheets().add(fileCss.toString());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}