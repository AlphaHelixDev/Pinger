/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.pinger;

import de.alphahelix.alphaapi.AlphaAPI;

public class Pinger extends AlphaAPI {

    private static Pinger instance;
    private static OptionsFile optionsFile = new OptionsFile();

    public static Pinger getInstance() {
        return instance;
    }

    static OptionsFile getOptionsFile() {
        return optionsFile;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        new PingCommand();
        new PingListener();
    }
}
