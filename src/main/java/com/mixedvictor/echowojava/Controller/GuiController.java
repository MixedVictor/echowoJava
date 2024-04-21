/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava.Controller;

import com.mixedvictor.echowojava.GlobalLogger;
import com.mixedvictor.echowojava.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class GuiController {
    private static final Clipboard clip = Clipboard.getSystemClipboard();
    private static final FileChooser dialog = new FileChooser();
    private static final Utils words = new Utils();

    private boolean addUwus = true;

    @FXML
    private TextArea editArea;
    @FXML
    private TextArea translateArea;

    @FXML
    private void initialize() {
        words.loadDefaultJson();

        // Translate the default text from editBox.
        handleTranslate();
    }

//    @FXML
//    private void handleSettings() {
//        // TODO: Handle settings.
//    }

    @FXML
    private void handleAddUwus() {
        addUwus = !addUwus;
    }

    @FXML
    private void handleTranslate() {
        if (addUwus) {
            translateArea.setText(words.uwusAdd(words.uwuifyString(editArea.getText())));
        } else {
            translateArea.setText(words.uwuifyString(editArea.getText()));
        }
    }

    @FXML
    private void handleCopy() {
        ClipboardContent clipContent = new ClipboardContent();
        clipContent.putString(translateArea.getText());
        clip.setContent(clipContent);
    }

    @FXML
    private void handlePaste() {
        if (clip.hasString()) {
            editArea.setText(clip.getString());
        } else {
            GlobalLogger.LOGGER.log(Level.INFO, "No clipboard string found");
        }
    }

    @FXML
    private void handleClear() {
        editArea.clear();
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private void handleSaveFile(ActionEvent event) {
        dialog.setTitle("Save file");
        dialog.setInitialDirectory(new File(System.getProperty("user.home")));

        File openedFile = dialog.showSaveDialog(((MenuItem) event.getSource()).getParentPopup().getOwnerNode().getScene().getWindow());

        if (openedFile != null) {
            try (PrintWriter writer = new PrintWriter(openedFile)) {
                writer.write(translateArea.getText());
            } catch (IOException e) {
                GlobalLogger.LOGGER.log(Level.SEVERE, "Error saving file", e);
            }
        } else {
            GlobalLogger.LOGGER.log(Level.INFO, "File not saved");
        }
    }

    @FXML
    private void handleOpenFile(ActionEvent event) {
        dialog.setTitle("Open file");

        File openedFile = dialog.showOpenDialog(((MenuItem) event.getSource()).getParentPopup().getOwnerNode().getScene().getWindow());

        if (openedFile != null) {
            try {
                editArea.setText(IOUtils.toString(new FileInputStream(openedFile), StandardCharsets.UTF_8));
            } catch (IOException e) {
                GlobalLogger.LOGGER.log(Level.WARNING, "Error reading file", e);
            }
        } else {
            GlobalLogger.LOGGER.log(Level.WARNING, "File not found");
        }
    }
}
