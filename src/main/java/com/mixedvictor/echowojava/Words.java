/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Words {
    private String[] uwus;
    private String[] common;
    private String[] uwuified;

    public Words() throws IOException {
        final URL fileJson = getClass().getClassLoader().getResource("default-words.json");
        if (fileJson != null) {
            JsonObject jsonObject = new Gson().fromJson(new BufferedReader(new InputStreamReader(fileJson.openStream())), JsonObject.class);
            uwus = jsonArrayToStringArray(jsonObject.getAsJsonArray("uwus"));
            common = jsonArrayToStringArray(jsonObject.getAsJsonArray("common"));
            uwuified = jsonArrayToStringArray(jsonObject.getAsJsonArray("uwuified"));
        }
    }

    private String[] jsonArrayToStringArray(JsonArray jsonArray) {
        String[] stringArray = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            stringArray[i] = jsonArray.get(i).getAsString();
        }
        return stringArray;
    }

    private int genInt(int max) {
        return (int) (Math.random() * (max));
    }

    public String uwusAdd(String str) {
        return String.format("%s %s %s", uwus[genInt(uwus.length)], str, uwus[genInt(uwus.length)]);
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