/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

public class Utils {
    private String[] uwus;
    private String[] common;
    private String[] uwuified;

    public void loadDefaultJson() {
        final URL fileJson = getClass().getClassLoader().getResource("default-words.json");
        if (fileJson != null) {
            try (BufferedReader jBr = new BufferedReader(new InputStreamReader(fileJson.openStream()))) {
                Utils words = new Gson().fromJson(jBr, Utils.class);
                uwus = words.uwus;
                common = words.common;
                uwuified = words.uwuified;
            } catch (IOException e) {
                GlobalLogger.LOGGER.log(Level.SEVERE, "Error reading 'default-words.json'", e);
            }
        }
    }

    private int genInt(int max) {
        return (int) (Math.random() * (max));
    }

    public String uwusAdd(String str) {
        return(uwus[genInt(uwus.length)] + " " + str + " " + uwus[genInt(uwus.length)]);
    }

    public String uwuifyString(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < common.length; i++) {
            int startIndex = str.toLowerCase().indexOf(common[i]);
            while (startIndex != -1) {
                for (int j = 0; j < common[i].length(); j++) {
                    char replaceChar = uwuified[i].charAt(j);
                    if (Character.isUpperCase(str.charAt(startIndex + j))) {
                        sb.setCharAt(startIndex + j, Character.toUpperCase(replaceChar));
                    } else {
                        sb.setCharAt(startIndex + j, Character.toLowerCase(replaceChar));
                    }
                }
                startIndex = str.toLowerCase().indexOf(common[i], startIndex + 1);
            }
        }
        return sb.toString();
    }
}