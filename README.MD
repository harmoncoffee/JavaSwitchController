# About the project 

### Description:
The goal of this project is to provide developers an alternative to c++
development language environment for those who want to interact with the 
Arduino Leonardo through serial programs in java.

I provide no assurances that this code will always work, if the 
PokemonAutomation team decides to change direction or provide a different solution it may stop functioning. 


References: 
https://github.com/PokemonAutomation/Microcontroller/blob/master/Wiki/Hardware/Hardware.md
https://github.com/PokemonAutomation/Microcontroller/blob/master/Wiki/Software/Windows-ArduinoLeonardo.md

### Inspiration: 
This project is inspired by the fine work done by: 

PokemonAutomation Team: https://github.com/PokemonAutomation

Main codebase this port/project is built off of:
- https://github.com/PokemonAutomation/Arduino-Source

Inspired by: 
- https://github.com/PokemonAutomation/ComputerControl 



### How to run
OS: Windows, currently only tested on Windows. 
Compiled on Java 17
Requirements: OpenCV 4.7.0 https://opencv.org/releases/ 
This needs to be added to your system properties if using windows. 

This also needs an Arduino Leonardo(not tested on anything else) for interacting with the  

### How to develop/contribute on

System Requirements: 
OpenCV 4.7.0 , Java 17 JDK, and Maven is used for dependency management. 

- You are welcome to raise a PR and I can review it when I get a chance. 
- I may likely add a discord channel if discussions are needed. 
- If you're doing development on the gui section, this uses IntellJ for the guidesigner. 

Need to confirm which works but: 
This needs to be added to your system properties PATH if using windows.
- \PathTo\opencv\build\bin
- \PathTo\opencv\build
- \PathTo\opencv\build\java
- \PathTo\opencv\build\java\x64

### How to add more programs. 

- Add program to this yaml: 
  - src/main/resources/programs.yaml 

```yaml
games:
  - name: Game 1
    programs:
      - name: Program 1
        javaClassName: com.example.Program1
      - name: Program 2
        javaClassName: com.example.Program2
  - name: Game 2
    programs:
      - name: Program 3
        javaClassName: com.example.Program3
      - name: Program 4
        javaClassName: com.example.Program4
```

- You can extend the program class. under com/pharmondev/computercontrol/programs
```java 
//example: 
public class PressTwoButtons extends Program{

  @Override
  public void run() {

    try {
      sendMessage(); //TODO document
      sendMessage(); //TODO document
    }
    catch (Exception e){

    }

  }
}


```

### Development Goals

This project is still super early in development. 
Many existing PokemonAutomation computer control functions do not exist yet.

Initial Goals:
- Complete all message type conversions to serial bytes. 
- Complete all button presses into serial/message byte formats. 
- Maybe rename Pabb, PabbPbf prefixes as I go. 
- Need to write something to deserialize all the message types. 
- Need to make sure all ui functions work
- Need to make UI prettier. 
  - trying to mirror existing computer control ui so existing users are familiar with the concept
- Get one console working for programs

Future Goals: 
- Add existing computer control features 
  - Multiple Consoles at one Time
  - Discord/Twitch/etc integrations
  - etc