package com.pharmondev.computercontrol.programs;

import com.pharmondev.computercontrol.messageprotocols.Message;
import com.pharmondev.computercontrol.utils.MessageSingleton;

import java.util.ArrayList;
import java.util.List;

public abstract class Program {


    public abstract void run();

    public Program(){
    }


    public void sendMessage(Message m) throws InterruptedException {
        MessageSingleton.getInstance().getSender().sendMessage(m);
    }
}
