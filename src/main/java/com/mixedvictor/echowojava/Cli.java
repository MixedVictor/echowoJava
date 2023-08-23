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

import java.io.*;
import java.net.URL;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.apache.commons.cli.*;
import com.google.gson.Gson;

public class Cli {
    private static Utils words;
    public static void main(String[] args) {
        Options options = new Options();
        Logger logger = Logger.getLogger(Cli.class.getName());

        URL fileJson = Cli.class.getClassLoader().getResource("words.json");
        if (fileJson != null) {
            try (BufferedReader jBr = new BufferedReader(
                    new InputStreamReader(fileJson.openStream()))) {
                words = new Gson().fromJson(jBr, Utils.class);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading 'words.json'", e);
            }
        }
        options.addOption(new Option("h", "help", false, "show help"));
        options.addOption(new Option("e", "echo", true, "echo something"));

        CommandLine cmd;

        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "Parsing error", e);
            return;
        }

        if (cmd.hasOption("block-size")) {
            System.out.println(cmd.getOptionValue("block-size"));
        }

        if (cmd.hasOption("h")) {
            new HelpFormatter().printHelp("echowoJava", options);
        } else if (cmd.hasOption("e")) {
            System.out.println(
                    words.uwusAdd(words.uwuifyString(cmd.getOptionValue("e")))
            );
        } else {
            System.out.println("If you want to use CLI, " +
                    "type -h in the arguments to see the commands"
            );
            Gui.main(null);
        }
    }
}