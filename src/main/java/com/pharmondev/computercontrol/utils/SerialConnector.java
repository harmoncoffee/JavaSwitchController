package com.pharmondev.computercontrol.utils;

import com.fazecast.jSerialComm.SerialPort;
import com.pharmondev.computercontrol.listeners.ArduinoListener;

public class SerialConnector {

    private static SerialConnector instance;

    private SerialPort port;

    private SerialConnector(){}

    static {
        try {
            instance = new SerialConnector();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    public static SerialConnector getInstance(){
        return instance;
    }

    public void openConnection(String portName){
        port = null;
        SerialPort[] ports = SerialPort.getCommPorts();

        for (SerialPort portoption : ports) {
            //System.out.println(portoption.getSystemPortName());
            if (port.getSystemPortName().equalsIgnoreCase("COM7")) {
                port = portoption;
            }
        }

        port.setBaudRate(115200);
        port.setNumDataBits(8);
        port.setNumStopBits(1);
        port.addDataListener(new ArduinoListener());

        port.openPort();


    }
    public void closeConnection(){
        if(port !=null && port.isOpen()){
            port.closePort();
        }
    }

    public void resetConnection(String portName){
        closeConnection();
        openConnection(portName);
    }

    public boolean isConnected(){
        if(port != null){
            return port.isOpen();
        }
        else {
            return false;
        }
    }

    public SerialPort getPort() {
        return port;
    }
}
