package com.pharmondev.computercontrol.crc32;

public class JNICRCImpl {
    static {
        System.loadLibrary("JavaSSE42DLL");
    }
    public static native int pabb_crc32_SSE42(int crc, byte[] data, int length);

}
