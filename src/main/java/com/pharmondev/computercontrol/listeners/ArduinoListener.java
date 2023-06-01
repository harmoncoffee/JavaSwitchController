/* (C)2023 */
package com.pharmondev.computercontrol.listeners;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.pharmondev.computercontrol.enums.MessageProtocol;

public class ArduinoListener implements SerialPortDataListener {
    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            System.out.println("Event: " + event.getEventType());
            // Data is available to read from the serial port
            byte[] newData = new byte[event.getSerialPort().bytesAvailable()];
            event.getSerialPort().readBytes(newData, newData.length);

            int length = ~newData[0];
            MessageProtocol protocol = MessageProtocol.getMessageProtocol(newData[1]);
            System.out.println("protocol:" + protocol.name());

        } else if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_WRITTEN) {
            // Data has been written to the serial port
            System.out.println("Event: " + event.getEventType());
            System.out.println("Data has been written to the serial port.");
        } else {
            System.out.println("Event: " + event.getEventType());
            System.out.println("Data has been written to the serial port.");
        }
        // ... Handle other event types if needed
    }
}
