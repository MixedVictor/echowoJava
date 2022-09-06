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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.cli.*;

import com.google.gson.Gson;

public class Cli {
   public static void main(String[] args) {
      HelpFormatter formatter = new HelpFormatter();
      final Options options = new Options();      

      Gson gson = new Gson();
      URL fileJson = Cli.class.getClassLoader().getResource(Words.JSON_PATH);
      BufferedReader br = null;

      try {
         br = new BufferedReader(new InputStreamReader(fileJson.openStream()));
      } catch (IOException e) {
         e.printStackTrace();
      }

      Words words = gson.fromJson(br, Words.class);

      // Examples on how to use
      //
      // String myString = "Hello World!";
      //
      // System.out.println(addUwus(myString);
      // System.out.println(uwuifyString(myString));
      //
      // System.out.println(words.addUwus(words.uwuifyString(myString)));

      options.addOption(new Option("e", "echo", true, "echo something"));
      options.addOption(new Option("h", "help", false, "show help"));
      options.addOption(new Option("g", "gui", false, "use gui"));
      
      CommandLineParser parser = new DefaultParser();
      // args = new String[]{"-g"};

      CommandLine cmd = null;

      try {
         cmd = parser.parse(options, args);
      } catch (ParseException e1) {
         e1.printStackTrace();
      }

      if (cmd.hasOption("block-size")) {
         System.out.println(cmd.getOptionValue("block-size"));
      }

      if (
         cmd.hasOption("h") == true  || 
         cmd.hasOption("e") == false &&
         cmd.hasOption("g") == false
         ) {
         formatter.printHelp("echowoJava", options); 
      }

      if (cmd.hasOption("g")) {
         Gui.main(null);
      }

      else if (cmd.hasOption("e")) {
         System.out.println(words.uwusOut(cmd.getOptionValues("e")[0]));
      }
   }
}