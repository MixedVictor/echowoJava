/* Copyright (C) 2022  MixedVictor
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.mixedvictor.echowojava;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.*;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

public class GuiController {
    @FXML
    private TextArea editArea;
    @FXML
    private TextArea translateArea;

    private static final Clipboard clip = Clipboard.getSystemClipboard();
    private static final FileChooser dialog = new FileChooser();
    private static final Logger logger = Logger.getLogger(
            GuiController.class.getName());
    @FXML
    private void initialize() {
        URL fileJson = getClass().getClassLoader().getResource("words.json");
        if (fileJson != null) {
            try (BufferedReader jBr = new BufferedReader(
                    new InputStreamReader(fileJson.openStream()))) {
                Cli.words = new Gson().fromJson(jBr, Utils.class);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading 'words.json'", e);
            }
        } else {
            logger.log(Level.WARNING, "Resource 'words.json' not found");
        }
        // Translate the default text from editBox.
        handleTranslate();
    }

    @FXML
    private void handleSaveFile(ActionEvent event) {
        dialog.setTitle("Save file");
        dialog.setInitialDirectory(new File(System.getProperty("user.home")));
        File openedFile = dialog.showSaveDialog(((MenuItem) event.getSource())
                .getParentPopup()
                .getOwnerNode()
                .getScene()
                .getWindow());

        if (openedFile != null) {
            try (PrintWriter writer = new PrintWriter(openedFile)) {
                writer.write(translateArea.getText());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error saving file", e);
            }
        } else {
            logger.log(Level.FINEST, "File not saved");
        }
    }

    @FXML
    private void handleOpenFile(ActionEvent event) {
        dialog.setTitle("Open file");
        File openedFile = dialog.showOpenDialog(((MenuItem) event.getSource())
                .getParentPopup()
                .getOwnerNode()
                .getScene()
                .getWindow());
        String bufferText;

        if (openedFile != null) {
            try {
                bufferText = IOUtils.toString(
                        new FileInputStream(openedFile), StandardCharsets.UTF_8
                );
                editArea.setText(bufferText);
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error reading file", e);
            }
        } else {
            logger.log(Level.WARNING, "File not found");
        }
    }

    @FXML
    private void handleTranslate() {
        translateArea.setText((Cli.words.uwusOut(editArea.getText())));
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
            logger.log(Level.FINEST, "No clipboard string found");
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
