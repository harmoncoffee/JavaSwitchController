/* (C)2023 */
package com.pharmondev.computercontrol.messageprotocols;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.constants.Constants;
import com.pharmondev.computercontrol.enums.MessageProtocol;
import java.nio.ByteBuffer;

public class MsgRequestSeqnumReset extends Message {
    public MsgRequestSeqnumReset(long seqNum) {
        this.type = MessageProtocol.PABB_MSG_SEQNUM_RESET;
        this.seqnum = UnsignedInteger.valueOf(seqNum);
    }

    @Override
    public byte[] constructSerialMessage() {
        byte[] results = null;
        int totalBytes = Constants.PABB_PROTOCOL_OVERHEAD + 4;
        ByteBuffer buffer = ByteBuffer.allocate(totalBytes);
        constructBytesUpToSeqNum(totalBytes, buffer);
        constructChecksumBytes(totalBytes, buffer);

        results = buffer.array();

        return results;
    }
}
