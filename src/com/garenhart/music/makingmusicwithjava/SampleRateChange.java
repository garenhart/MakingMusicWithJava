package com.garenhart.music.makingmusicwithjava;

import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public class SampleRateChange {

    public static void main(String[] args) {
        float[] data = Read.audio("data/Welcome.au");
        writeAudio(data, "Welcome22.aif", 1, 22050, 16);
        writeAudio(data, "Welcome48.aif", 1, 48000, 16);
    }
}
