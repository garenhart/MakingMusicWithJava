package com.garenhart.music.makingmusicwithjava;

import jm.util.*;

public class ReadAudio {

    public static void main(String[] args) {
        float[] data = Read.audio("data/Jazz Improvisation L2 - F7 Altered.wav");

        for (int i=0; i<data.length; i++) {
            System.out.print(data[i] + " ");
            if (i % 10 == 0)
                System.out.println();
        }
    }
}
