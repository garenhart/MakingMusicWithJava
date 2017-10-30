package com.garenhart.music.makingmusicwithjava;

import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public class Recombine {
    float[] data = Read.audio("data/Welcome.au");

    public static void main(String[] args) {
        Recombine recomb =  new Recombine();
        writeAudio(recomb.process(), "Recombined.aif");
        View.au("data/out/Recombined.aif");
    }

    private float[] process() {
        float[] seg1 = newSegment();
        float[] seg2 = newSegment();
        float[] seg3 = newSegment();

        // assemble
        float[] newData  = new float[seg1.length +  seg2.length + seg3.length];
        int newDataCounter = 0;
        for(int i=0; i<seg1.length; i++) {
            newData[newDataCounter++] = seg1[i];
        }
        for(int i=0; i<seg2.length; i++) {
            newData[newDataCounter++] = seg2[i];
        }
        for(int i=0; i<seg3.length; i++) {
            newData[newDataCounter++] = seg3[i];
        }

        return newData;
    }

    private float[] newSegment() {
        float fadeSize = 500f;
        int length = (int)(Math.random() * data.length / 2) + (int)(fadeSize * 2);
        int startLocation = (int)(Math.random() * (data.length - fadeSize * 2 - length));

        float[] tempData = new float[length];
        for (int i=0; i<length; i++) {
            tempData[i] = data[startLocation + i];
        }
        for (int i=0; i<fadeSize; i++) {
            tempData[i] *=  i/ fadeSize;
        }
        for (int i=0; i<fadeSize; i++) {
            tempData[tempData.length - i - 1] *=  i/ fadeSize;
        }
        return tempData;
    }
}