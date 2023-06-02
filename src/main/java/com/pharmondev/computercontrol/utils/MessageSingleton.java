package com.pharmondev.computercontrol.utils;

import com.pharmondev.computercontrol.listeners.MessageQueue;
import com.pharmondev.computercontrol.listeners.Sender;
import com.pharmondev.computercontrol.listeners.Receiver;


//TODO this will only support 1 Console at a time.
//TODO Consider another option
public class MessageSingleton {
    private static MessageSingleton instance;
    private MessageQueue messageQueue;
    private Sender sender;
    private Receiver receiver;

    private MessageSingleton() {
        // Private constructor to prevent instantiation from outside the class
        messageQueue = new MessageQueue();
        sender = new Sender(messageQueue);
        receiver = new Receiver(messageQueue);
    }

    public static MessageSingleton getInstance() {
        if (instance == null) {
            synchronized (MessageSingleton.class) {
                if (instance == null) {
                    instance = new MessageSingleton();
                }
            }
        }
        return instance;
    }

    public Sender getSender() {
        return sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void resetSequenceNumber(){
        messageQueue.setAckSequenceNumber(1);
    }
}
