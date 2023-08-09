package com.vadimtch.applet_template.src;

import javacard.framework.*;

public class MyApplet extends Applet {
    private MyApplet() {}

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MyApplet().register();
    }

    @Override
    public void process(APDU apdu) throws ISOException {
        if (apdu.isISOInterindustryCLA()) return;

        byte[] buffer = apdu.getBuffer();
        short cla = UnsignedByte.decode(buffer[ISO7816.OFFSET_CLA]);
        short ins = UnsignedByte.decode(buffer[ISO7816.OFFSET_INS]);
        short p1 = UnsignedByte.decode(buffer[ISO7816.OFFSET_P1]);
        short p2 = UnsignedByte.decode(buffer[ISO7816.OFFSET_P2]);

        if (!isValidCLA(cla)) ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
        if (apdu.isCommandChainingCLA()) ISOException.throwIt(ISO7816.SW_COMMAND_CHAINING_NOT_SUPPORTED);
        if (apdu.isSecureMessagingCLA()) ISOException.throwIt(ISO7816.SW_SECURE_MESSAGING_NOT_SUPPORTED);

        if (cla != 0xA0) ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
        if (ins != 0x42) ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);

        buffer[0] = UnsignedByte.encode((short) (p1 + p2));
        apdu.setOutgoingAndSend((short) 0, (short) 1);
    }

    @Override
    public boolean select() {
        return true;
    }

    /**
     * Checks if the given CLA is valid.
     * <p>
     * The function is provided as a replacement for {@link APDU#isValidCLA()}, due to a bug in JCardSim, the emulator
     * currently used for testing, where this function always returns {@code false}.
     * <p>
     * The function mimics the behaviour of {@link APDU#isValidCLA()} as defined in the JavaCard 3.0.5. standard
     * (<a href="https://docs.oracle.com/javacard/3.0.5/api/javacard/framework/APDU.html#isValidCLA()">see here</a>).
     */
    private boolean isValidCLA(short cla) {
        // Invalid CLA byte according to ISO 7816-4:2013
        if (cla == 0xFF) return false;

        // Reserved for future use according to ISO 7816-4:2013 (three highest bits equal to {@code 001})
        // 0xE0 = 0b11100000, 0x20 = 0b00100000 (hexadecimal literals used for compatibility with earlier Java versions)
        if ((cla & 0xE0) == 0x20) return false;

        return true;
    }
}