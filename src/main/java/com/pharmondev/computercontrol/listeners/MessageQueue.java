package com.pharmondev.computercontrol.listeners;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.messageprotocols.Message;
import com.pharmondev.computercontrol.utils.SerialConnector;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private Queue<Message> queue;
    private int maintainedSequenceNumber;
    private int expectedSequenceNumber;

    public MessageQueue() {
        queue = new LinkedList<>();
        maintainedSequenceNumber = 1;
        expectedSequenceNumber = -1;
    }

    public synchronized void enqueue(Message message) {
        //queue.add(message); GENERATED CODE don't think I need for sending message.
        message.setSeqnum(UnsignedInteger.valueOf(maintainedSequenceNumber));
        if(SerialConnector.getInstance().isConnected()){
            byte[] constructedMessage = message.constructSerialMessage();
            SerialConnector.getInstance().getPort().writeBytes(constructedMessage,constructedMessage.length);
        }

        notifyAll();
        maintainedSequenceNumber++;
    }

    public synchronized Message dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.remove();
    }

    public synchronized boolean isAckExpected(int sequenceNumber) {
        return sequenceNumber == expectedSequenceNumber;
    }

    public synchronized void acknowledge(int sequenceNumber) {
        if (sequenceNumber == expectedSequenceNumber) {
            notifyAll();
        }
    }

    public int getMaintainedSequenceNumber() {
        return maintainedSequenceNumber;
    }

    public void setMaintainedSequenceNumber(int maintainedSequenceNumber) {
        this.maintainedSequenceNumber = maintainedSequenceNumber;
    }

    public int getExpectedSequenceNumber() {
        return expectedSequenceNumber;
    }

    public void setAckSequenceNumber(int expectedSequenceNumber) {
        this.expectedSequenceNumber = expectedSequenceNumber;
    }
}
