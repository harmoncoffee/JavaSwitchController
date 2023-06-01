/* (C)2023 */
package com.pharmondev.computercontrol.crc32;

import java.util.zip.CRC32C;

public class CRC32COverride {
    private CRC32C crc32;

    public CRC32COverride() {
        crc32 = new CRC32C();
    }

    public void update(byte[] data, int offset, int length) {
        crc32.update(data, offset, length);
    }

    public void reset() {
        crc32.reset();
    }

    public long getValue() {

        long evaluatedValue = crc32.getValue();
        long undoneValue = (~((int) (~evaluatedValue & 0xFFFFFFFFL))) & 0xFFFFFFFFL;
        long expectedChecksum = ~undoneValue;
        return expectedChecksum;
    }

    public long getUnsignedValue() {

        long evaluatedValue = crc32.getValue();
        long undoneValue = (~((int) (~evaluatedValue & 0xFFFFFFFFL))) & 0xFFFFFFFFL;
        long expectedChecksum = ~undoneValue;

        return expectedChecksum & 0xFFFFFFFFL;
    }
}
