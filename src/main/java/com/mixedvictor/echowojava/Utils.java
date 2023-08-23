/* Copyright (C) 2023  MixedVictor
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

import java.lang.Math;

public class Utils {
    private int genInt(int max) {
        return (int) (Math.random() * (max));
    }

    public String[] uwus;
    public String[] common;
    public String[] uwuified;

    public String uwusAdd(String str) {
        return (uwus[genInt(uwus.length)] + " " +
                str + " " + uwus[genInt(uwus.length)]);
    }

    public String uwuifyString(String str) {
        for (int i = 0; i < common.length; i++) {
            str = str.replace(common[i], uwuified[i]);
        }
        return (str);
    }
}