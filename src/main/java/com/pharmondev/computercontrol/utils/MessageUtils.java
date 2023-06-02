package com.pharmondev.computercontrol.utils;

import com.pharmondev.computercontrol.enums.MessageProtocol;
import com.pharmondev.computercontrol.messageprotocols.Message;

public class MessageUtils {
    public static Message determineMessage(MessageProtocol protocol, byte[] data, int length){

        Message message = null;

        //TODO case statement to "construct" messages based off type


        return message;
    }

    //TODO if message just has SEQNUM they can be bundled in same unserialization manner
    //MsgRequestSeqnumReset
    //MsgRequestStop
    //pabb_end_program_callback
    //pabb_MsgAckCommand
    //pabb_MsgAckRequest
    //pabb_MsgInfoCommandDropped
    //pabb_MsgInfoInvalidRequest
    //pabb_MsgInfoMissedRequest
    //pabb_MsgRequestNextCmdInterrupt
    //pabb_MsgRequestProgramID
    //pabb_MsgRequestProgramVersion
    //pabb_MsgRequestProtocolVersion

    //TODO  seq + byte data 1 byte?
    //pabb_MsgAckRequestI8
    //pabb_MsgCommandSetLeds TODO is this boolean just 0,1

    //TODO seq + data (short)? 2 byte?
    //pabb_MsgAckRequestI8

    //TODO seq + data (int)? 4 byte?
    //pabb_MsgAckRequestI32

    //TODO message_length only? no seq? or is this fail from chatgpt
    //pabb_MsgInfoChecksumMismatch
    //pabb_MsgInfoInvalidMessage
    //

    //TODO byte tag, and data int (4 byte?) no seq?
    //pabb_MsgInfoI32

    //TODO byte only? no seq?
    //pabb_MsgInfoInvalidType

    //TODO short(2 byte) errorcode
    //pabb_MsgInfoWARNING

    //TODO seq (4 byte) and seq of original message, and int finish_time
    //pabb_MsgRequestCommandFinished

}
