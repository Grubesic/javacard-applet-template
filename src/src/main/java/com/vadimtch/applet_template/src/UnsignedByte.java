package com.vadimtch.applet_template.src;

/**
 * A utility class that compensates for the lack of {@code unsigned byte} in Java.
 * <p>
 * A {@code byte} in Java is 8 bits long, and, therefore, can store 256 unique values.
 * However, it is a signed data type, which means that Java will interpret the bits as a two's complement value,
 * limiting the range of {@code byte} to [-128, 127].
 * <p>
 * This class provides methods to re-interpret the bits of a {@code byte} as an unsigned value (stored as a
 * {@code short}) to utilise the full potential range of [0, 255] a {@code byte} in cases where a signed value is
 * not needed.
 */
public class UnsignedByte {
    private UnsignedByte() {}

    /**
     * Reinterprets the bits of a {@code byte} as an unsigned value, returned as a short
     */
    public static short decode(byte bits) {
        return (short)(bits & 0x00FF);
    }

    /**
     * Stores the bits of a {@code short} in a {@code byte} to encode a signed value in the range [0, 255]
     * for storage in a {@code byte}
     */
    public static byte encode(short number) {
        if ((number < 0) || (number > 255)) throw new IllegalArgumentException("Number must be in the range [0, 255]");

        return (byte)(number & 0x00FF);
    }
}
