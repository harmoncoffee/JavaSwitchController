package com.pharmondev.computercontrol.programs;


import com.pharmondev.computercontrol.messageprotocols.MsgRequestSeqnumReset;
import com.pharmondev.computercontrol.messageprotocols.MsgRequestStop;
import com.pharmondev.computercontrol.utils.MessageSingleton;

public class StartupProgram extends Program{

    @Override
    public void run() {

        try {
            MessageSingleton.getInstance().resetSequenceNumber();
            sendMessage(new MsgRequestStop(1));
            MessageSingleton.getInstance().resetSequenceNumber();
            sendMessage(new MsgRequestSeqnumReset());
        }
        catch (Exception e){

        }

    }
}
