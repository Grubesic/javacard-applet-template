package com.vadimtch.applet_template.test;

import com.licel.jcardsim.smartcardio.CardSimulator;
import com.licel.jcardsim.utils.AIDUtil;
import com.vadimtch.applet_template.src.MyApplet;
import javacard.framework.AID;

public class SimulatorWrapper {
    private final CardSimulator simulator;
    private static final SimulatorWrapper instance = new SimulatorWrapper();

    private SimulatorWrapper() {
        simulator = new CardSimulator();
        AID appletAID = AIDUtil.create("544553544150504C455401");

        simulator.installApplet(appletAID, MyApplet.class);
        simulator.selectApplet(appletAID);
    }

    public static CardSimulator getSimulator() {
        return SimulatorWrapper.instance.simulator;
    }
}