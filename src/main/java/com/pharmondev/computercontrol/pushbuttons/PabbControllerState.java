/* (C)2023 */
package com.pharmondev.computercontrol.pushbuttons;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.enums.Button;
import com.pharmondev.computercontrol.enums.DpadPosition;

public class PabbControllerState {
    public static final int PABB_MSG_CONTROLLER_STATE = 0x9f;

    public UnsignedInteger seqnum;
    public Button button;
    public DpadPosition dpad;
    public int left_joystick_x;
    public int left_joystick_y;
    public int right_joystick_x;
    public int right_joystick_y;
    public int ticks;
}
