//// Copyright (C) 2022  MixedVictor
////
//// This program is free software; you can redistribute it and/or
//// modify it under the terms of the GNU General Public License
//// as published by the Free Software Foundation; either version 2
//// of the License, or (at your option) any later version.
////
//// This program is distributed in the hope that it will be useful,
//// but WITHOUT ANY WARRANTY; without even the implied warranty of
//// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//// GNU General Public License for more details.
////
//// You should have received a copy of the GNU General Public License
//// along with this program; if not, write to the Free Software
//// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//

package com.mixedvictor.echowojava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        GuiController controller = loader.getController();
        // You can pass any necessary dependencies to the controller here

        stage.setTitle("echowoJava");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//public class Gui extends Application {
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("echowoJava");
//        primaryStage.setResizable(false);
//
//        VBox rootVbox = new VBox(7);
//        HBox rootHbox = new HBox(7);
//
//        FileChooser oFileDialog = new FileChooser();
//        oFileDialog.setTitle("Open Text File");
//
//        MenuBar mainBar = new MenuBar();
//
//        Menu mFile = new Menu("File");
//        MenuItem oFile = new MenuItem("Open");
//
//        Clipboard clip = Clipboard.getSystemClipboard();
//        ClipboardContent clipContent = new ClipboardContent();
//
//        TextArea inputArea = new TextArea();
//        TextArea outputArea = new TextArea();
//
//        Button refButton = new Button();
//        Button copyButton = new Button();
//        Button pasteButton = new Button();
//
//        Gson gson = new Gson();
//        URL fileJson = Gui.class.getClassLoader().getResource(Words.JSON_PATH);
//
//        BufferedReader jBr = null;
//
//        try {
//            jBr = new BufferedReader(new InputStreamReader(fileJson.openStream()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Words words = gson.fromJson(jBr, Words.class);
//
//        inputArea.setText("Put something here");
//        inputArea.setWrapText(true);
//        outputArea.setEditable(false);
//        outputArea.setWrapText(true);
//        rootHbox.setAlignment(Pos.CENTER);
//        refButton.setText("UwUify");
//        copyButton.setText("Copy");
//        pasteButton.setText("Paste");
//
//        Scene scene = new Scene(rootVbox, 426, 240);
//
//        // Open text file
//        oFile.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//                File openedFile = oFileDialog.showOpenDialog(primaryStage);
//                FileInputStream fileOp = null;
//                String fBrText = null;
//
//                if (openedFile != null) {
//                    try {
//                        fileOp = new FileInputStream(openedFile);
//                        fBrText = IOUtils.toString(fileOp, StandardCharsets.UTF_8);
//                        inputArea.setText(fBrText);
//                        outputArea.setText(fBrText);
//                    } catch (IOException io) {
//                        System.out.println("IOException");
//                    }
//                }
//            }
//        });
//
//        // UwUifies the text
//        refButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                outputArea.setText((words.uwusOut(inputArea.getText())));
//            }
//        });
//
//        // Copies the text
//        copyButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                clipContent.putString(outputArea.getText());
//                clip.setContent(clipContent);
//            }
//        });
//
//        // Pastes the text
//        pasteButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (clip.hasString()) {
//                    inputArea.setText(clip.getString());
//                    outputArea.setText(words.uwusOut(clip.getString()));
//                }
//            }
//        });
//
//        rootVbox.getChildren().add(mainBar);
//        mainBar.getMenus().addAll(mFile, oConf);
//        mFile.getItems().addAll(oFile);
//
//        rootVbox.getChildren().add(inputArea);
//        VBox.setMargin(inputArea, new Insets(5, 5, 1, 5));
//
//        rootVbox.getChildren().addAll(rootHbox);
//        rootHbox.getChildren().addAll(refButton, copyButton, pasteButton);
//        HBox.setMargin(refButton, new Insets(1, 1, 1, 5));
//        HBox.setMargin(copyButton, new Insets(1, 1, 1, 5));
//        HBox.setMargin(pasteButton, new Insets(1, 1, 1, 5));
//
//        rootVbox.getChildren().add(outputArea);
//        VBox.setMargin(outputArea, new Insets(1, 5, 5, 5));
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}