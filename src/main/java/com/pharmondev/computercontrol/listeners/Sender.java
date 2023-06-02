package com.pharmondev.computercontrol.listeners;

import com.pharmondev.computercontrol.messageprotocols.Message;
import com.pharmondev.computercontrol.programs.Program;

public class Sender implements Runnable {
    private MessageQueue messageQueue;

    public Sender(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void sendMessage(Message message) throws InterruptedException {
        synchronized (messageQueue) {
            messageQueue.enqueue(message);
            if(message.isAckRequired()){
                messageQueue.setAckSequenceNumber(message.getSeqnum().intValue());
                while (!messageQueue.isAckExpected(message.getSeqnum().intValue())) {
                    messageQueue.wait();
                }
            }
        }
    }

    @Override
    public void run() {
        // Code for sending messages
        // ...
    }

}