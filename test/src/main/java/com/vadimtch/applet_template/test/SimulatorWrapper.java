package com.vadimtch.applet_template.test;

import com.licel.jcardsim.smartcardio.CardSimulator;
import com.licel.jcardsim.utils.AIDUtil;
import com.vadimtch.applet_template.src.MyApplet;
import javacard.framework.AID;
import javacard.framework.Applet;

/**
 * A singleton class that generates, sets up, and manages a shared JavaCard simulator instance for all tests.
 * <p>
 * Configure the ${@code static final} fields below to match the AID and class of your applet.
 */
public class SimulatorWrapper {
    /**
     * Applet AID - this must match the applet AID set in the build configuration.
     */
    private static final AID appletAID = AIDUtil.create("544553544150504C455401");

    /**
     * Applet main class - set this to the class that extends {@link Applet} in your applet project.
     */
    private static final Class<? extends Applet> appletClass = MyApplet.class;

    /**
     * Reset the simulator to its initial state. All persistent data stored in the applets is cleared as well.
     */
    public void resetSimulator() {
        this.simulator = SimulatorWrapper.initialiseSimulator();
    }

    /**
     * Reselect the applet (triggers a call to {@link Applet#select()}).
     */
    public void reselectApplet() {
        this.simulator.selectApplet(appletAID);
    }

    /**
     * Get the shared simulator instance.
     */
    public static CardSimulator getSimulator() {
        return SimulatorWrapper.instance.simulator;
    }

    private CardSimulator simulator;
    private static final SimulatorWrapper instance = new SimulatorWrapper();

    private SimulatorWrapper() {
        simulator = initialiseSimulator();
    }

    private static CardSimulator initialiseSimulator() {
        CardSimulator simulator = new CardSimulator();

        simulator.installApplet(appletAID, appletClass);
        simulator.selectApplet(appletAID);

        return simulator;
    }
}