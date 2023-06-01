/* (C)2023 */
package com.pharmondev.computercontrol.enums;

public enum Button {
    BUTTON_Y(1 << 0),
    BUTTON_B(1 << 1),
    BUTTON_A(1 << 2),
    BUTTON_X(1 << 3),
    BUTTON_L(1 << 4),
    BUTTON_R(1 << 5),
    BUTTON_ZL(1 << 6),
    BUTTON_ZR(1 << 7),
    BUTTON_MINUS(1 << 8),
    BUTTON_PLUS(1 << 9),
    BUTTON_LCLICK(1 << 10),
    BUTTON_RCLICK(1 << 11),
    BUTTON_HOME(1 << 12),
    BUTTON_CAPTURE(1 << 13);

    private final int value;

    Button(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
