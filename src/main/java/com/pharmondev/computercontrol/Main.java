/* (C)2023 */
package com.pharmondev.computercontrol;

import com.fazecast.jSerialComm.*;
import com.pharmondev.computercontrol.gui.ControlGui;
import com.pharmondev.computercontrol.listeners.ArduinoListener;
import com.pharmondev.computercontrol.messageprotocols.MsgRequestSeqnumReset;
import javax.swing.*;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import org.opencv.core.Core;

public class Main {
    public static void main(String[] args)
            throws InterruptedException, UnsupportedLookAndFeelException {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));

        ControlGui gui = new ControlGui();
        JFrame frame = new JFrame();
        frame.setContentPane(gui.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void testSerialPorts() {
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
        //MsgRequestSeqnumReset msgRequestSeqnumReset = new MsgRequestSeqnumReset(1);

        //        mainPort.writeBytes(
        //                msgRequestStop.constructSerialMessage(),
        //                msgRequestStop.constructSerialMessage().length);
//        mainPort.writeBytes(
//                msgRequestSeqnumReset.constructSerialMessage(),
//                msgRequestSeqnumReset.constructSerialMessage().length);

        // check zero to 40.

        while (mainPort.isOpen()) {}
    }

    public void connect() {

        // MsgRequestStop stopMessage = new MsgRequestStop();

    }
}
