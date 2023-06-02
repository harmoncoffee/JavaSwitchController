/* (C)2023 */
import static org.junit.Assert.assertEquals;

import com.google.common.primitives.UnsignedInteger;
import com.pharmondev.computercontrol.crc32.CRC32COverride;
import com.pharmondev.computercontrol.messageprotocols.MsgRequestSeqnumReset;
import com.pharmondev.computercontrol.messageprotocols.MsgRequestStop;
import org.junit.Test;

public class Tester {

    //    *  Message Format:
    //    *      byte 0: Length of the entire message. (bits are inverted)
    //    *      byte 1: Message Type
    //    *      byte X: Optional data of variable length.
    //    *      Last 4 bytes: CRC32C of the entire message except these last 4 bytes.
    //    *
    //    *      Thus there are 6 bytes of overhead for each message.

    @Test
    public void testUnsignedByteInversion() {
        // if message is:
        // 10 bytes
        // length: 10
        // byte 0: Length of message inverted (10 -> //10 -> 4294967285 or 0xf5)
        // byte 1: STOP = 0x46
        // byte X: seq
        // last 4 bytes: CRC32C of entire message.
        // last 4 =
        //        'k' 	107    	0x6b
        //        'ힿ�'	4294967220	0xb4
        //        'ힿ�'	4294967191	0x97
        //        'ힿ�'	4294967256	0xd8

        int size = 465;
        long invertedSize = ~size & 0xFFFFFFFFL;

        System.out.println("size:" + size);
        System.out.println("~size:" + invertedSize);

        UnsignedInteger size2 = UnsignedInteger.valueOf(size);
        UnsignedInteger size3 = UnsignedInteger.valueOf(invertedSize);

        System.out.println("size2:" + size2);
        System.out.println("size3:" + size3);
    }

    // if message is:
    // 10 bytes
    // length: 10
    // byte 0: Length of message inverted (10 -> //10 -> 4294967285 or 0xf5)
    // byte 1: STOP = 0x46
    // byte X: seq
    // last 4 bytes: CRC32C of entire message.
    // last 4 =
    //        'k' 	107    	0x6b
    //        'ힿ�'	4294967220	0xb4
    //        'ힿ�'	4294967191	0x97
    //        'ힿ�'	4294967256	0xd8
    @Test
    public void testCRC32() {
        byte[] bytes = new byte[10];

        bytes[0] = (byte) -11;
        bytes[1] = (byte) 70;
        bytes[2] = (byte) 1;
        bytes[3] = (byte) 0;
        bytes[4] = (byte) 0;
        bytes[5] = (byte) 0;
        //        bytes[6] = (byte) 0; //these need to be used for checksum
        //        bytes[7] = (byte) 0;
        //        bytes[8] = (byte) 0;
        //        bytes[9] = (byte) 0;

        CRC32COverride crc32cjavaimplementation = new CRC32COverride();
        crc32cjavaimplementation.update(bytes, 0, 6);
        int crc32cj = (int) crc32cjavaimplementation.getValue();
        long crc32cUnsignedj = crc32cjavaimplementation.getUnsignedValue();
        System.out.println("CRC Using java:" + crc32cj);
        System.out.println("unsignedResult CRC Using java:" + crc32cUnsignedj);
        // expecting: 107,-76,-105, -40
        // expected checksum = 3633820779

        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (crc32cUnsignedj & 0xFF);
        byteArray[1] = (byte) ((crc32cUnsignedj >> 8) & 0xFF);
        byteArray[2] = (byte) ((crc32cUnsignedj >> 16) & 0xFF);
        byteArray[3] = (byte) ((crc32cUnsignedj >> 24) & 0xFF);

        assertEquals(3633820779L, crc32cUnsignedj);
        assertEquals(byteArray[0], 107);
        assertEquals(byteArray[1], -76);
        assertEquals(byteArray[2], -105);
        assertEquals(byteArray[3], -40);
    }

    @Test
    public void testMessagesSerial() {

        MsgRequestStop msgRequestStop = new MsgRequestStop(1);
        byte[] msgRequestStopResults = msgRequestStop.constructSerialMessage();
        assertEquals(msgRequestStopResults[0], -11);
        assertEquals(msgRequestStopResults[1], 70);
        assertEquals(msgRequestStopResults[2], 1);
        assertEquals(msgRequestStopResults[3], 0);
        assertEquals(msgRequestStopResults[4], 0);
        assertEquals(msgRequestStopResults[5], 0);
        assertEquals(msgRequestStopResults[6], 107);
        assertEquals(msgRequestStopResults[7], -76);
        assertEquals(msgRequestStopResults[8], -105);
        assertEquals(msgRequestStopResults[9], -40);

        MsgRequestSeqnumReset msgRequestSeqnumReset = new MsgRequestSeqnumReset();
        msgRequestSeqnumReset.setSeqnum(UnsignedInteger.valueOf(1));
        byte[] msgRequestSeqnumResetResults = msgRequestSeqnumReset.constructSerialMessage();
        assertEquals(msgRequestSeqnumResetResults[0], -11);
        assertEquals(msgRequestSeqnumResetResults[1], 64);
        assertEquals(msgRequestSeqnumResetResults[2], 1);
        assertEquals(msgRequestSeqnumResetResults[3], 0);
        assertEquals(msgRequestSeqnumResetResults[4], 0);
        assertEquals(msgRequestSeqnumResetResults[5], 0);
        assertEquals(msgRequestSeqnumResetResults[6], -125);
        assertEquals(msgRequestSeqnumResetResults[7], -43);
        assertEquals(msgRequestSeqnumResetResults[8], -16);
        assertEquals(msgRequestSeqnumResetResults[9], 72);
    }

    @Test
    public void testBaudRate() {

        long baudrate = 115200;

        byte[] bytesForBaudRate = new byte[4];

        bytesForBaudRate[0] = (byte) (baudrate & 0xFF);
        bytesForBaudRate[1] = (byte) ((baudrate >> 8) & 0xFF);
        bytesForBaudRate[2] = (byte) ((baudrate >> 16) & 0xFF);
        bytesForBaudRate[3] = (byte) ((baudrate >> 24) & 0xFF);

        for (byte b : bytesForBaudRate) {

            System.out.println(b);
        }
    }
}
// 2023-05-29 16:07:12.461493 - [Console 0]: Sending: pbf_press_button() - seqnum = 465, button = 2(
// BUTTON_B ), hold_ticks = 20, release_ticks = 50
//        2023-05-29 16:07:12.461507 - [stdout]: 2023-05-29 16:07:12.461499 - buffer START:
//        2023-05-29 16:07:12.461519 - [stdout]: 2023-05-29 16:07:12.461514 - buffer:-17
//        2023-05-29 16:07:12.461527 - [stdout]: 2023-05-29 16:07:12.461523 - buffer:-111
//        2023-05-29 16:07:12.461536 - [stdout]: 2023-05-29 16:07:12.461532 - buffer:-47
//        2023-05-29 16:07:12.461546 - [stdout]: 2023-05-29 16:07:12.461541 - buffer:1
//        2023-05-29 16:07:12.461556 - [stdout]: 2023-05-29 16:07:12.461552 - buffer:0
//        2023-05-29 16:07:12.461565 - [stdout]: 2023-05-29 16:07:12.461559 - buffer:0
//        2023-05-29 16:07:12.461573 - [stdout]: 2023-05-29 16:07:12.461570 - buffer:2
//        2023-05-29 16:07:12.461588 - [stdout]: 2023-05-29 16:07:12.461582 - buffer:0
//        2023-05-29 16:07:12.461596 - [stdout]: 2023-05-29 16:07:12.461593 - buffer:20
//        2023-05-29 16:07:12.461609 - [stdout]: 2023-05-29 16:07:12.461604 - buffer:0
//        2023-05-29 16:07:12.461623 - [stdout]: 2023-05-29 16:07:12.461617 - buffer:50
//        2023-05-29 16:07:12.461640 - [stdout]: 2023-05-29 16:07:12.461634 - buffer:0
//        2023-05-29 16:07:12.461654 - [stdout]: 2023-05-29 16:07:12.461649 - buffer:-108
//        2023-05-29 16:07:12.461669 - [stdout]: 2023-05-29 16:07:12.461664 - buffer:74
//        2023-05-29 16:07:12.461682 - [stdout]: 2023-05-29 16:07:12.461676 - buffer:-94
//        2023-05-29 16:07:12.461699 - [stdout]: 2023-05-29 16:07:12.461692 - buffer:-91
//        2023-05-29 16:07:12.461714 - [stdout]: 2023-05-29 16:07:12.461706 - buffer END:
