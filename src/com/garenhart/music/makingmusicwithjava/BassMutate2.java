package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

public class BassMutate2 implements JMC {
    Phrase bassLine1 = new Phrase(0.0);
    Phrase bassLine2 = new Phrase(0.0);
    int phraseSize = 16;
    int[] availablePitches = {36, 36, 40, 43, 45, 48, 48, 52, 55, REST, REST, REST};
	
    public static void main(String[] args) {
        new BassMutate2();
    }
	
    public BassMutate2() {
        Part bassPart = new Part(SYNTH_BASS);
        bassPart.setTempo(130.0);
        // create parent phrase 1
        for (int i = 0; i<phraseSize; i++) {
            int pitch = availablePitches[(int)(Math.random() * availablePitches.length)];
            bassLine1.addNote(new Note(pitch, 0.25));
        }
        // create parent phrase 2
        for (int i = 0; i<phraseSize; i++) {
            int pitch = availablePitches[(int)(Math.random() * availablePitches.length)];
            bassLine2.addNote(new Note(pitch, 0.25));
        }

        for (int i=0; i < 8; i++) {
            Phrase childBassLine1 = createChild();
            Phrase childBassLine2 = createChild();
			
            childBassLine1.setStartTime(8.0 * i);
            childBassLine2.setStartTime(8.0 * i + 4.0);
            bassPart.addPhrase(childBassLine1);
            bassPart.addPhrase(childBassLine2);
			
            bassLine1 = childBassLine1;
            bassLine2 = childBassLine2;
        }

        View.show(bassPart);
        Play.midi(bassPart);
    }
	
    private Phrase createChild() {
        Phrase tempPhrase = new Phrase();
        // crossover
        int crossOverPoint = (int)(Math.random() * phraseSize);
        for(int j=0; j<crossOverPoint; j++) {
            tempPhrase.addNote(bassLine1.getNote(j).copy());
        }
        for(int j=crossOverPoint; j<phraseSize; j++) {
            tempPhrase.addNote(bassLine2.getNote(j).copy());
        }
        // mutate
        Note n = tempPhrase.getNote((int)(Math.random() * phraseSize));
        int pitch = availablePitches[(int)(Math.random() * availablePitches.length)];
        n.setPitch(pitch);
		
        return tempPhrase;
    }
}