package com.vadimtch.applet_template.src;


public class UnsignedByte {
    private UnsignedByte() {}

    public static short decode(byte bits) {
        return (short)(bits & 0x00FF);
    }

    public static byte encode(short number) {
        return (byte)(number & 0x00FF);
    }
}
