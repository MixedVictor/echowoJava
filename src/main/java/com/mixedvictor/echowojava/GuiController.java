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

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

public class GuiController {
    //    @FXML
//    VBox rootVbox;
    @FXML
    HBox rootHbox;
    @FXML
    FileChooser openDialog;
    //    @FXML
//    FileChooser saveDialog;
    @FXML
    Clipboard clip;
    @FXML
    ClipboardContent clipContent;
    @FXML
    Button translateButton;
    @FXML
    Button copyButton;
    @FXML
    Button pasteButton;
    @FXML
    TextArea editBox;
    @FXML
    TextArea translateBox;

    Gson gson;
    Words words;

    private static final Logger logger = Logger.getLogger(GuiController.class.getName());

    @FXML
    private void initialize() {
        gson = new Gson();
        URL fileJson = getClass().getClassLoader().getResource("words.json");
        if (fileJson != null) {
            try (BufferedReader jBr = new BufferedReader(
                    new InputStreamReader(fileJson.openStream()))) {
                words = gson.fromJson(jBr, Words.class);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading 'words.json'", e);
            }
        } else {
            logger.log(Level.WARNING, "Resource 'words.json' not found");
        }
    }

    @FXML
    private void handleSaveFile() {
        /* TODO: File saver. */
    }

    @FXML
    private void handleOpenFile(ActionEvent event) {
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        File openedFile = openDialog.showOpenDialog(stage);
        String fBrText;

        if (openedFile != null) {
            try {
                fBrText = IOUtils.toString(
                        new FileInputStream(openedFile), StandardCharsets.UTF_8
                );
                editBox.setText(fBrText);
                translateBox.setText(fBrText);
            } catch (IOException e) {
                System.out.println("IOException: file not found.");
            }
        }
    }

    @FXML
    private void handleTranslate() {
        translateBox.setText((words.uwusOut(editBox.getText())));
    }

    @FXML
    private void handleCopy() {
        clipContent.putString(translateBox.getText());
        clip.setContent(clipContent);
    }

    @FXML
    private void handlePaste() {
        if (clip.hasString()) {
            editBox.setText(clip.getString());
            translateBox.setText(words.uwusOut(clip.getString()));
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
