package com.garenhart.music.makingmusicwithjava;

import jm.midi.MidiCommunication;

public class MidiComTest extends MidiCommunication {

    public static void main(String[] args) {
        new MidiComTest();
    }

    public MidiComTest() {
        sendMidiOutput(176, 0, 7, 80); // volume
        sendMidiOutput(144, 0, 60, 100); // note on
        try {
            Thread.sleep(500);
        }catch(Exception e) {}
        sendMidiOutput(128, 0, 60, 0); // note off
    }

    public void handleMidiInput(int type,int channel,int data1,int data2) {
        System.out.println("MIDI IN - [type: " + type + "] [Channel: " + channel + "] [Data byte 1: " + data1 + "] [Data byte 2: " + data2 + "]");
    }
}
