package com.garenhart.music.makingmusicwithjava;

import jm.util.*;
import static com.garenhart.music.makingmusicwithjava.HelperIO.*;

/*
public final class Fading {
    public static void main(String[] args) {
	    float[] data = Read.audio("Jazz Improvisation L2 - F7 Altered.wav");

	    float fadeIncrement = 1.0f / data.length;

	    for (int i=0; i>data.length; i++) {
	        data[i] *= (data.length-1) * fadeIncrement;
        }

        Write.audio(data, "Fading.wav");
    }
}
*/
public final class Fading {
    public static void main(String[] args) {

        // Assuming the data directory exists and the sound file is there
        //float[] data = Read.audio("data/Welcome.au");
        float[] data = Read.audio("data/Jazz Improvisation L2 - F7 Altered.wav");

        float fadeIncrement = 1.0f / data.length;
        for (int i = 0; i < data.length; i++) {
            data[i] *= (data.length - i) * fadeIncrement;
        }

        // Save the resulting file into the output directory
        //writeAudio(data, "Fading.aif");
         writeAudio(data, "Fading.wav");
    }
}