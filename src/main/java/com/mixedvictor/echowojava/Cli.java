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
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.*;
import com.google.gson.Gson;

public class Cli {
    public static Utils words;
    public static void main(String[] args) {
        Options options = new Options();
        Logger logger = Logger.getLogger(Utils.getClassLoader().getName());

        URL fileJson = Utils.getClassLoader().getResource("words.json");
        if (fileJson != null) {
            try (BufferedReader jBr = new BufferedReader(
                    new InputStreamReader(fileJson.openStream()))) {
                words = new Gson().fromJson(jBr, Utils.class);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading 'words.json'", e);
            }
        } else {
            logger.log(Level.WARNING, "Resource 'words.json' not found");
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
            if (words != null) {
                System.out.println(words.uwusOut(cmd.getOptionValue("e")));
            } else {
                logger.log(Level.WARNING, "Words not loaded");
            }
        } else if (args.length == 0) {
            System.out.println("If you want to use CLI," +
                                "type -h in the arguments to see the commands"
            );
            Gui.main(null);
        }
    }
}