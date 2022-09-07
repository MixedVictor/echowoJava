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

import java.lang.Math;

public class Words {
    public static final String JSON_PATH = "words.json";
    public static final String DEFAULT_CSS_FILE = "css/gui.css";

    private static int genInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String[] uwus;
    public String[] standard;
    public String[] uwuify;

    public String addUwus(String str) {
        String s = " ";
        String randUwuF = uwus[genInt(0, uwus.length)];
        String randUwuS = uwus[genInt(0, uwus.length)];
        return(randUwuF + s + str + s + randUwuS);
    }

    public String uwuifyString(String str) {
        for (int i = 0; i < standard.length; i++) {
            str = str.replace(standard[i], uwuify[i]);
        }
        return(str);
    }
    
    public String uwusOut(String str) { return(addUwus(uwuifyString(str))); }
}