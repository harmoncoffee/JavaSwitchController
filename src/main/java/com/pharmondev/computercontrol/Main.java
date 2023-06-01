/* (C)2023 */
package com.pharmondev.computercontrol;

import com.fazecast.jSerialComm.*;
import com.pharmondev.computercontrol.listeners.ArduinoListener;
import com.pharmondev.computercontrol.messageprotocols.MsgRequestSeqnumReset;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Get the port to which your device is connected
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Ports amount" + ports.length);
        SerialPort mainPort = null;
        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName());
            if (port.getSystemPortName().equalsIgnoreCase("COM7")) {
                mainPort = port;
            }
        }
        mainPort.setBaudRate(115200);
        mainPort.setNumDataBits(8);
        mainPort.setNumStopBits(1);

        mainPort.addDataListener(new ArduinoListener());

        System.out.println("Listener Added: ");
        System.out.println("Port opening: ");
        mainPort.openPort();
        System.out.println("Port opened: ");

        // msgRequestStop = new MsgRequestStop(1);
        MsgRequestSeqnumReset msgRequestSeqnumReset = new MsgRequestSeqnumReset(1);

        //        mainPort.writeBytes(
        //                msgRequestStop.constructSerialMessage(),
        //                msgRequestStop.constructSerialMessage().length);
        mainPort.writeBytes(
                msgRequestSeqnumReset.constructSerialMessage(),
                msgRequestSeqnumReset.constructSerialMessage().length);

        // check zero to 40.

        while (mainPort.isOpen()) {}
    }

    public void connect() {

        // MsgRequestStop stopMessage = new MsgRequestStop();

    }
}
