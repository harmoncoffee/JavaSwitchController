/* (C)2023 */
package com.pharmondev.computercontrol.utils;

public class LittleEndianConverter {
    public static int bigEndianToInt(byte[] bytes) {
        return (bytes[0] & 0xFF) << 24
                | (bytes[1] & 0xFF) << 16
                | (bytes[2] & 0xFF) << 8
                | (bytes[3] & 0xFF);
    }

    public static byte[] intToLittleEndian(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value & 0xFF);
        bytes[1] = (byte) ((value >> 8) & 0xFF);
        bytes[2] = (byte) ((value >> 16) & 0xFF);
        bytes[3] = (byte) ((value >> 24) & 0xFF);
        return bytes;
    }

    public static byte[] longToLittleEndian(long value) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }

    public static long littleEndianToLong(byte[] bytes) {
        long value = 0;
        for (int i = 7; i >= 0; i--) {
            value <<= 8;
            value |= (bytes[i] & 0xFF);
        }
        return value;
    }
}
