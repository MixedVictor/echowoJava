/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava.Gui;

import ch.bailu.gtk.adw.Application;
import ch.bailu.gtk.adw.ApplicationWindow;
import ch.bailu.gtk.adw.Toast;
import ch.bailu.gtk.adw.ToastOverlay;
import ch.bailu.gtk.gio.MenuModel;
import ch.bailu.gtk.gtk.*;
import ch.bailu.gtk.lib.bridge.UiBuilder;
import ch.bailu.gtk.lib.handler.action.ActionHandler;
import ch.bailu.gtk.type.exception.AllocationError;
import com.mixedvictor.echowojava.Words;

import java.io.IOException;

public class Builder {
    private static final TextIter start = new TextIter();
    private static final TextIter end = new TextIter();
    private static boolean uwuToggle = true;

    public Builder(Application app) throws AllocationError, IOException {
        Words words = new Words();
        UiBuilder builder = UiBuilder.fromResource("/application.xml");
        ApplicationWindow window = new ApplicationWindow(builder.getObject("window"));

        // Translate button
        Button translate_button = new Button(builder.getObject("translate_button"));
//        translate_button.addCssClass("suggested-action");

        // Clear button
        Button clear_button = new Button(builder.getObject("clear_button"));
        clear_button.addCssClass("destructive-action");

        // Copy button
//        Button copy_button = new Button(builder.getObject("copy_button"));

        // Paste button
//        Button paste_button = new Button(builder.getObject("paste_button"));
        clear_button.addCssClass("destructive-action");

        // Toggle UwU's button
        ToggleButton toggle_uwus = new ToggleButton(builder.getObject("toggle_uwus"));
        toggle_uwus.onToggled(() -> uwuToggle = !uwuToggle);

        // Menu stuff
        MenuButton menu_button = new MenuButton(builder.getObject("menu_button"));
        menu_button.setMenuModel(new MenuModel(builder.getObject("menu_app")));

        // Textview stuff
        TextView edit_area = new TextView(builder.getObject("edit_area"));
        TextView translate_area = new TextView(builder.getObject("translate_area"));

        // Toast stuff
        ToastOverlay toast_overlay = new ToastOverlay(builder.getObject("toast_overlay"));

        // Handling stuff
        // Why on Earth setAccels need to have the 'app' at start???
        ActionHandler.setAccels(app, "app.open", "<Ctrl>o");
        ActionHandler.setAccels(app, "app.save", "<Ctrl>s");
        ActionHandler.setAccels(app, "app.about", "F1");

        ActionHandler.get(app, "about").onActivate(() -> new About(app));

        TextBuffer buffer_translate = translate_area.getBuffer();
        TextBuffer buffer_edit = edit_area.getBuffer();

        translate_button.onClicked(() -> uwuifyBufferToTextView(buffer_edit, buffer_translate, toast_overlay, words));
        clear_button.onClicked(() -> clearBuffer(buffer_edit));
//        copy_button.onClicked(() -> {
//            buffer_translate.copyClipboard();
//        });

        // CSS stuff
        Separator spacer_start = new Separator(builder.getObject("spacer_start"));
        Separator spacer_end = new Separator(builder.getObject("spacer_end"));
        spacer_start.addCssClass("spacer");
        spacer_end.addCssClass("spacer");

        window.addCssClass("devel");
        window.setSizeRequest(640, 480);
        window.setApplication(app);
        window.present();
    }

    private static void uwuifyBufferToTextView(TextBuffer buffer, TextBuffer field_buffer, ToastOverlay toast_overlay, Words words) {
        buffer.getBounds(start, end);
        final String text = String.valueOf(buffer.getText(start, end, false));

        // If buffer is empty send toast
        if (text.isEmpty()) {
            Toast toast = new Toast("Input field is empty");
            toast.setTimeout(2);
            toast_overlay.addToast(toast);
        } else {
            if (uwuToggle) {
                field_buffer.setText(words.uwusAdd(words.uwuifyString(text)), -1);
            } else {
                field_buffer.setText(words.uwuifyString(text), -1);
            }
        }
    }

    private static void clearBuffer(TextBuffer buffer) {
        buffer.getBounds(start, end);
        buffer.delete(start, end);
    }
}
