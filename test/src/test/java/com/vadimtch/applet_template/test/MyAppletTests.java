package com.vadimtch.applet_template.test;

import com.licel.jcardsim.smartcardio.CardSimulator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class MyAppletTests {
    private CardSimulator simulator;

    @Before
    public void setUp() {
        simulator = SimulatorWrapper.getSimulator();
    }

    @Test
    public void test() {
        CommandAPDU command = new CommandAPDU(0xA0, 0x42, 0x01, 0x02, 0x01);
        ResponseAPDU response = simulator.transmitCommand(command);

        Assert.assertEquals(0x9000, response.getSW());
        Assert.assertEquals(0x03, response.getData()[0]);
    }
}
