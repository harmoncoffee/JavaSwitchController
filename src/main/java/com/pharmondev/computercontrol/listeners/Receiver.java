package com.pharmondev.computercontrol.listeners;

import com.pharmondev.computercontrol.enums.MessageProtocol;
import com.pharmondev.computercontrol.messageprotocols.Message;

public class Receiver implements Runnable {
    private MessageQueue messageQueue;

    public Receiver(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void receiveMessage(Message message) {
        synchronized (messageQueue) {
            if (message.getType().equals(MessageProtocol.PABB_MSG_ACK_COMMAND) ||
                message.getType().equals(MessageProtocol.PABB_MSG_ACK_REQUEST) ||
                    message.getType().equals(MessageProtocol.PABB_MSG_ACK_REQUEST_I8) ||
                    message.getType().equals(MessageProtocol.PABB_MSG_ACK_REQUEST_I16)||
                    message.getType().equals(MessageProtocol.PABB_MSG_ACK_REQUEST_I32)
            ) {
                messageQueue.acknowledge(message.getSeqnum().intValue());
            }
        }
    }

    @Override
    public void run() {
        // Code for receiving messages
        // ...
    }
}
