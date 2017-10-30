package com.garenhart.music.makingmusicwithjava;

import jm.util.Read;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public class AudioMixer {
    float[] data1, data2;

    public static void main(String[] args) {
        AudioMixer audioMixer = new AudioMixer();

        audioMixer.process();

    }

    private void process() {
        data1 = Read.audio("data/Welcome.au");
        data2 = Read.audio("data/RainStick.wav");

        float[] mixedData;

        mixedData = (data1.length > data2.length) ? mix(data1, data2) : mix(data2, data1);

        writeAudio(mixedData, "MixedAudio.wav");
    }

    private float[] mix(float[] longest, float[] shortest) {
        float[] data = new float[longest.length];

        for (int i=0; i<shortest.length; i++) {
            data[i] =(longest[i] + shortest[i]) *0.5f;
        }

        for (int i=shortest.length; i<longest.length; i++) {
            data[i] = longest[i] * 0.5f;
        }

        return data;
    }
}
