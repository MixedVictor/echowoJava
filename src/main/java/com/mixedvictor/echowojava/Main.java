/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava;

import org.apache.commons.cli.*;

import java.util.logging.Level;

public class Main {

    private static void printHelp(HelpFormatter formatter, Options options) {
        formatter.printHelp("echowoJava <string>", options);
    }

    public static void main(String[] args) {
        Utils words = new Utils();
        words.loadDefaultJson();

        Options options = new Options();

        options.addOption(new Option("h", "help", false, "show help"));
        options.addOption(new Option(null, "uwuless", false, "do not include uwu's"));

        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = new DefaultParser().parse(options, args);
            if (cmd.hasOption("block-size")) {
                System.out.println(cmd.getOptionValue("block-size"));
            }
            if (cmd.hasOption("h")) {
                printHelp(formatter, options);
            } else if (args.length != 0) {
                for (String inputString : cmd.getArgs()) {
                    if (cmd.hasOption("uwuless")) {
                        System.out.println(words.uwuifyString(inputString));
                    } else {
                        System.out.println(words.uwusAdd(words.uwuifyString(inputString)));
                    }
                }
            } else {
                System.out.println("If you want to use CLI, type -h in the arguments to see the commands");
                Gui.main(args);
            }
        } catch (ParseException exp) {
            GlobalLogger.LOGGER.log(Level.WARNING, "Parsing error", exp);
            printHelp(formatter, options);
        }
    }
}