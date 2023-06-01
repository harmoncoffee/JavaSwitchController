/* (C)2023 */
package com.pharmondev.computercontrol.enums;

public enum MessageProtocol {
    PABB_MSG_ERROR_READY((byte) 0x00),
    PABB_MSG_ERROR_INVALID_MESSAGE((byte) 0x01),
    PABB_MSG_ERROR_CHECKSUM_MISMATCH((byte) 0x02),
    PABB_MSG_ERROR_INVALID_TYPE((byte) 0x03),
    PABB_MSG_ERROR_INVALID_REQUEST((byte) 0x04),
    PABB_MSG_ERROR_MISSED_REQUEST((byte) 0x05),
    PABB_MSG_ERROR_COMMAND_DROPPED((byte) 0x06),
    PABB_MSG_ERROR_WARNING((byte) 0x07),
    PABB_MSG_ACK_COMMAND((byte) 0x10),
    PABB_MSG_ACK_REQUEST((byte) 0x11),
    PABB_MSG_ACK_REQUEST_I8((byte) 0x12),
    PABB_MSG_ACK_REQUEST_I16((byte) 0x13),
    PABB_MSG_ACK_REQUEST_I32((byte) 0x14),
    PABB_MSG_SEQNUM_RESET((byte) 0x40),
    PABB_MSG_REQUEST_PROTOCOL_VERSION((byte) 0x41),
    PABB_MSG_REQUEST_PROGRAM_VERSION((byte) 0x42),
    PABB_MSG_REQUEST_PROGRAM_ID((byte) 0x43),
    PABB_MSG_REQUEST_CLOCK((byte) 0x44),
    PABB_MSG_REQUEST_COMMAND_FINISHED((byte) 0x45),
    PABB_MSG_REQUEST_STOP((byte) 0x46),
    PABB_MSG_REQUEST_NEXT_CMD_INTERRUPT((byte) 0x47),
    PABB_MSG_INFO_I32((byte) 0x20),
    PABB_MSG_COMMAND_END_PROGRAM_CALLBACK((byte) 0x80),
    PABB_MSG_COMMAND_SET_LED_STATE((byte) 0x81);

    private final byte value;

    MessageProtocol(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static MessageProtocol getMessageProtocol(byte value) {
        for (MessageProtocol messageProtocol : MessageProtocol.values()) {
            if (messageProtocol.getValue() == value) {
                return messageProtocol;
            }
        }
        return null;
    }
}
