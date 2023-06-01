/* (C)2023 */
package com.pharmondev.computercontrol.messageprotocols;

import com.google.common.primitives.UnsignedInteger;

public class pabb_MsgRequestCommandFinished {
    public UnsignedInteger seqnum;
    public UnsignedInteger seq_of_original_command;
    public int finish_time;
}
