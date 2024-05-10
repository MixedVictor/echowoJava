/* Copyright (C) 2024  MixedVictor
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.mixedvictor.echowojava.Gui;

import ch.bailu.gtk.adw.AboutWindow;
import ch.bailu.gtk.adw.Application;
import ch.bailu.gtk.gtk.License;

public class About {
    public About(Application app) {
        AboutWindow about = new AboutWindow();
        about.setTransientFor(app.getActiveWindow());
        about.setApplicationName("echowoJava");
        about.setVersion("1.0");
        about.setComments("Text uwuifier written in Java");
        about.setLicenseType(License.MPL_2_0);
        about.setCopyright("Copyright (C) 2024 MixedVictor");
        about.setDeveloperName("MixedVictor");
        about.present();
    }
}
