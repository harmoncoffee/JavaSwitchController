/* (C)2023 */
package com.pharmondev.computercontrol.pushbuttons;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.enums.DpadPosition;

public class PabbPbfPressDpad {
    public static final int PABB_MSG_COMMAND_PBF_PRESS_DPAD = 0x92;
    public UnsignedInteger seqnum;
    public DpadPosition dpad;
    public int hold_ticks;
    public int release_ticks;
}
