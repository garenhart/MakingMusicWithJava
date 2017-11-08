package com.garenhart.music.makingmusicwithjava;

import jm.music.data.*;
import jm.JMC;
import jm.audio.*;
import jm.util.*;

/*
* This class shows each jMusic noise type
*/
public class AllNoiseTypes implements JMC {
	
    public static void main(String[] args) {
            new AllNoiseTypes();
    }
    
    public AllNoiseTypes() {
        // create a score
        Score score = new Score(new Part(new Phrase(new Note(60, 4.0))));
        // loop through each noise type in jMusic
        for (int i=0; i<6; i++ ) {
            // set up audio instrument. Sample rate, Channels, Noise type.
            Instrument wave = new NoiseInst(44100, 1, i);
            // Render an audio file of the score
            Write.au(score, "NoiseType" + i + ".au", wave);
            // Display the audio file
            View.au("NoiseType" + i + ".au", i*20, i*20);
        }
    }
}

	
