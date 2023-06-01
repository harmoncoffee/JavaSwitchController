/* (C)2023 */
package com.pharmondev.computercontrol.pushbuttons;

import com.google.common.primitives.UnsignedInteger;

public class PabbPbfMoveJoystick {
    public static final int PABB_MSG_COMMAND_PBF_MOVE_JOYSTICK_L = 0x93;
    public static final int PABB_MSG_COMMAND_PBF_MOVE_JOYSTICK_R = 0x94;
    public UnsignedInteger seqnum;
    public int x;
    public int y;
    public int hold_ticks;
    public int release_ticks;
}
