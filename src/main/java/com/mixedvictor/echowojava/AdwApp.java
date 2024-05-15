/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava;

import ch.bailu.gtk.adw.AboutWindow;
import ch.bailu.gtk.adw.Application;
import ch.bailu.gtk.adw.ApplicationWindow;
import ch.bailu.gtk.adw.Toast;
import ch.bailu.gtk.adw.ToastOverlay;
import ch.bailu.gtk.gio.MenuModel;
import ch.bailu.gtk.gtk.*;
import ch.bailu.gtk.lib.bridge.UiBuilder;
import ch.bailu.gtk.lib.handler.action.ActionHandler;
import ch.bailu.gtk.type.exception.AllocationError;

import java.io.IOException;

public class AdwApp {
    private final TextIter start = new TextIter();
    private final TextIter end = new TextIter();
    private boolean uwuToggle = true;

    public AdwApp(Application app) {
        UiBuilder builder = null, menu_builder = null;
        try {
            builder = UiBuilder.fromResource("/ui/application.xml");
            menu_builder = UiBuilder.fromResource("/ui/menu.xml");
        } catch (IOException | AllocationError e) {
            e.printStackTrace();
        }

        var window = new ApplicationWindow(builder.getObject("window"));
        var translate_button = new Button(builder.getObject("translate_button"));
        var clear_button = new Button(builder.getObject("clear_button"));
        var edit_area = new TextView(builder.getObject("edit_area"));
        var translate_area = new TextView(builder.getObject("translate_area"));
        var menu_button = new MenuButton(builder.getObject("menu_button"));
        var toast_overlay = new ToastOverlay(builder.getObject("toast_overlay"));

        var buffer_translate = translate_area.getBuffer();
        var buffer_edit = edit_area.getBuffer();

        // Handling stuff
        // Why on Earth setAccels need to have the 'app' at start???
        ActionHandler.setAccels(app, "app.open", "<Ctrl>o");
        ActionHandler.setAccels(app, "app.save", "<Ctrl>s");
        ActionHandler.setAccels(app, "app.about", "F1");

        ActionHandler.get(app, "about").onActivate(() -> showAbout(app));
        ActionHandler.get(app, "translate_text")
                .onActivate(() -> uwuifyBufferToTextView(buffer_edit, buffer_translate, toast_overlay));
        ActionHandler.get(app, "clear_text").onActivate(() -> clearBuffer(buffer_edit));
        ActionHandler.get(app, "toggle_uwus").onActivate(() -> uwuToggle = !uwuToggle);

        menu_button.setMenuModel(new MenuModel(menu_builder.getObject("menu")));

        // CSS stuff
        translate_button.addCssClass("suggested-action");
        clear_button.addCssClass("destructive-action");

        window.setApplication(app);
        window.present();
    }

    private void uwuifyBufferToTextView(TextBuffer buffer, TextBuffer field_buffer, ToastOverlay toast_overlay) {
        buffer.getBounds(start, end);
        final String text = String.valueOf(buffer.getText(start, end, false));

        // If buffer is empty send toast
        if (text.isEmpty()) {
            Toast toast = new Toast("Input field is empty");
            toast.setTimeout(2);
            toast_overlay.addToast(toast);
        } else {
            Words words = new Words();
            if (uwuToggle) {
                field_buffer.setText(words.uwusAdd(words.uwuifyString(text)), -1);
            } else {
                field_buffer.setText(words.uwuifyString(text), -1);
            }
        }
    }

    private void clearBuffer(TextBuffer buffer) {
        buffer.getBounds(start, end);
        buffer.delete(start, end);
    }

    private void showAbout(Application app) {
        AboutWindow about = new AboutWindow();
        about.setTransientFor(app.getActiveWindow());
        about.setApplicationName("echowoJava");
        about.setVersion("1.0");
        about.setComments("Text uwuifier written in Java using java-gtk");
        about.setLicenseType(License.MPL_2_0);
        about.setCopyright("Copyright (C) 2024 MixedVictor");
        about.setDeveloperName("MixedVictor");
        about.present();
    }
}
