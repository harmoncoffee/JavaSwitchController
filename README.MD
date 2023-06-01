# Notes



When you first connect to the serial. It seems to run a reset: 0x46 PABB_MSG_REQUEST_STOP  

So message would be based off of: https://github.com/PokemonAutomation/Arduino-Source/blob/main/Common/Microcontroller/MessageProtocol.h
```
*  Message Format:
*      byte 0: Length of the entire message. (bits are inverted)
*      byte 1: Message Type
*      byte X: Optional data of variable length.
*      Last 4 bytes: CRC32C of the entire message except these last 4 bytes.
*
*      Thus there are 6 bytes of overhead for each message.
```

Sequence of events for Startup of serial connection: 

- We run a stop message MsgRequestStop Type 0x46 seq 1
- We run a device seq num reset message. Type 0x40 seq 1
  - wait for ack response 
- 
