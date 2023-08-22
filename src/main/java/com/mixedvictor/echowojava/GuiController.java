package com.mixedvictor.echowojava;


import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import javafx.fxml.FXML;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

public class GuiController {
    @FXML
    private VBox rootVbox;
    @FXML
    private HBox rootHbox;
    @FXML
    private FileChooser oFileDialog;
    @FXML
    private FileChooser oCssDialog;
    @FXML
    private Clipboard clip;
    @FXML
    private ClipboardContent clipContent;
    @FXML
    private MenuBar mainBar;
    @FXML
    private Button refButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button pasteButton;
    @FXML
    private TextArea editBox;
    @FXML
    private TextArea translateBox;

    private Gson gson;
    private Words words;
    
    @FXML
    private void initialize() {
        gson = new Gson();
        URL fileJson = getClass().getClassLoader().getResource("words.json");
        try (BufferedReader jBr = new BufferedReader(new InputStreamReader(fileJson.openStream()))) {
            words = gson.fromJson(jBr, Words.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveFile(ActionEvent event) {
        /* TODO: File saver. */
    }

    @FXML
    private void handleOpenFile(ActionEvent event) {
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        File openedFile = oFileDialog.showOpenDialog(stage);
        String fBrText;

        if (openedFile != null) {
            try {
                fBrText = IOUtils.toString(new FileInputStream(openedFile), StandardCharsets.UTF_8);
                editBox.setText(fBrText);
                translateBox.setText(fBrText);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    private void handleTranslate(ActionEvent event) {
        translateBox.setText((words.uwusOut(editBox.getText())));
    }
    
    @FXML
    private void handleCopy(ActionEvent event) {
        clipContent.putString(translateBox.getText());
        clip.setContent(clipContent);
    }

    @FXML
    private void handlePaste(ActionEvent event) {
        if (clip.hasString()) {
            editBox.setText(clip.getString());
            translateBox.setText(words.uwusOut(clip.getString()));
        }
    }
}
