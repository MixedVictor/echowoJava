/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mixedvictor.echowojava;

import ch.bailu.gtk.adw.Application;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.type.Strs;

import org.apache.commons.cli.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static void printHelp(HelpFormatter formatter, Options options) {
        formatter.printHelp("echowoJava <string>", options);
    }

    public static void main(String[] args) {
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
                Words words = new Words();
                for (String inputString : cmd.getArgs()) {
                    if (cmd.hasOption("uwuless")) {
                        System.out.println(words.uwuifyString(inputString));
                    } else {
                        System.out.println(words.uwusAdd(words.uwuifyString(inputString)));
                    }
                }
            } else {
                final Application app = new Application("com.mixedvictor.echowoJava", ApplicationFlags.NON_UNIQUE);
                app.onActivate(() -> new AdwApp(app));
                app.run(args.length, new Strs(args));
                app.unref();
            }
        } catch (ParseException e) {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Parsing error", e);
            printHelp(formatter, options);
        }
    }
}