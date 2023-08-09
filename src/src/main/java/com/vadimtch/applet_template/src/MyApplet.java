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

    private boolean isValidCLA(short cla) {
        if (cla == 0xFF) return false;
        if ((cla & 0xE0) == 0x20) return false;

        return true;
    }
}