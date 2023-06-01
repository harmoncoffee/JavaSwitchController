/* (C)2023 */
package com.pharmondev.computercontrol.pushbuttons;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.enums.Button;

public class PabbPbfMashButton {
    public static final int PABB_MSG_COMMAND_MASH_BUTTON = 0x95;

    public UnsignedInteger seqnum;
    public Button button;
    public int ticks;
}
