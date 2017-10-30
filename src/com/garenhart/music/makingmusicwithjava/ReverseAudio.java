package com.garenhart.music.makingmusicwithjava;

import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public class ReverseAudio {

    public static void main(String[] args) {
        float[] data = Read.audio("data/Welcome.au");
        float[] dataReversed = new float[data.length];

        for (int i=0; i<data.length; i++) {
            dataReversed[data.length-i-1] = data[i];
        }

        writeAudio(dataReversed, "Reversed.wav");
    }
}
