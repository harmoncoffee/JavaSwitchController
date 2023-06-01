/* (C)2023 */
package com.pharmondev.computercontrol.pushbuttons;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.enums.Button;

public class PabbPbfPressButton {
    public static final int PABB_MSG_COMMAND_PBF_PRESS_BUTTON = 0x91;

    public UnsignedInteger seqnum;
    public Button button;
    public int hold_ticks;
    public int release_ticks;
}
