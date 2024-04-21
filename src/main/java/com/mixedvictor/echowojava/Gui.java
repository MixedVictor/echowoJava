/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("echowoJava");
        stage.setScene(new Scene(new FXMLLoader(getClass().getClassLoader().getResource("main.fxml")).load()));

        double width = 640;
        double height = 480;

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setMinWidth(440);
        stage.setMinHeight(360);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        stage.show();

        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }
}
