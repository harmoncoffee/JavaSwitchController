/* (C)2023 */
package com.pharmondev.computercontrol.enums;

public enum DpadPosition {
    DPAD_UP(0),
    DPAD_UP_RIGHT(1),
    DPAD_RIGHT(2),
    DPAD_DOWN_RIGHT(3),
    DPAD_DOWN(4),
    DPAD_DOWN_LEFT(5),
    DPAD_LEFT(6),
    DPAD_UP_LEFT(7),
    DPAD_NONE(8);

    private final int value;

    DpadPosition(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
