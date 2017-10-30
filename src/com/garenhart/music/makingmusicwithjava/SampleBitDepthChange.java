package com.garenhart.music.makingmusicwithjava;

import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public class SampleBitDepthChange {

    public static void main(String[] args) {
        float[] data = Read.audio("data/Welcome.au");
        writeAudio(data, "Welcome44_8.aif",1, 44100, 8);
        writeAudio(data, "Welcome44_32.aif",1, 44100, 32);
    }
}
