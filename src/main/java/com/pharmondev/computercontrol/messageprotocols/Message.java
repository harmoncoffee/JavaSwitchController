/* (C)2023 */
package com.pharmondev.computercontrol.messageprotocols;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.crc32.CRC32COverride;
import com.pharmondev.computercontrol.enums.MessageProtocol;
import java.nio.ByteBuffer;

public abstract class Message {
    long size;
    long invertedSize;
    UnsignedInteger seqnum;
    MessageProtocol type;

    boolean isAckRequired;

    public abstract byte[] constructSerialMessage();

    public char getInvertedBytesChar(int size) {
        return (char) (~size & 0xFFFFFFFFL);
    }

    public void constructChecksumBytes(int totalBytes, ByteBuffer buffer) {
        CRC32COverride crc = new CRC32COverride();
        byte[] bytesForChecksum = buffer.array();
        crc.update(bytesForChecksum, 0, (int) buffer.array().length - 4);
        long crcdata = crc.getUnsignedValue();
        buffer.position(totalBytes - 4);

        buffer.put((byte) (crcdata & 0xFF));
        buffer.put((byte) ((crcdata >> 8) & 0xFF));
        buffer.put((byte) ((crcdata >> 16) & 0xFF));
        buffer.put((byte) ((crcdata >> 24) & 0xFF));
    }

    public void constructBytesUpToSeqNum(int totalBytes, ByteBuffer buffer) {
        buffer.put((byte) getInvertedBytesChar(totalBytes));
        buffer.put(this.type.getValue());
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(seqnum.intValue());

        byte[] reversedSeqBytes = reverseByteBuffer(byteBuffer);
        for (byte b : reversedSeqBytes) {
            buffer.put(b);
        }
        buffer.rewind();
    }

    public byte[] reverseByteBuffer(ByteBuffer byteBuffer) {
        int length = byteBuffer.array().length;
        byte[] reversedBytes = new byte[length];

        for (int i = length - 1; i >= 0; i--) {
            reversedBytes[length - 1 - i] = byteBuffer.get(i);
        }

        return reversedBytes;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getInvertedSize() {
        return invertedSize;
    }

    public void setInvertedSize(long invertedSize) {
        this.invertedSize = invertedSize;
    }

    public UnsignedInteger getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(UnsignedInteger seqnum) {
        this.seqnum = seqnum;
    }

    public MessageProtocol getType() {
        return type;
    }

    public void setType(MessageProtocol type) {
        this.type = type;
    }

    public boolean isAckRequired() {
        return isAckRequired;
    }

    public void setAckRequired(boolean ackRequired) {
        isAckRequired = ackRequired;
    }
}
