package com.garenhart.music.makingmusicwithjava;

import com.garenhart.music.makingmusicwithjava.SineInst;
import jm.JMC;
import jm.music.data.*;
import jm.music.tools.*;
import jm.util.*;
import jm.audio.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.viewAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

/**
* This class turns a series of pitches into a repeating arpeggio
* @author Andrew Brown
*/

public class Arpeggio implements JMC {
	
    public static void main(String[] args) {
        new Arpeggio();
    }
    
    public Arpeggio() {
        int[] pitches = {D2, D2, D3, D3, A2, A3, F3, C3};
        // turn pitches into a phrase
        Phrase arpPhrase = new Phrase();
        for(int i = 0; i < 16; i++) {
            int pitchIndex = (int)(Math.random() * pitches.length);
            Note n = new Note(pitches[pitchIndex], EIGHTH_NOTE);
            arpPhrase.addNote(n);
        }
        // repeat the arpeggio a few times
        Mod.repeat(arpPhrase, 4);
        // pack into a part, adding and instrument
        Part arpPart = new Part("Arpeggio Part", 0, arpPhrase);
        Score score = new Score("Arpeggio Score", 145, arpPart);
        // create an audio instrument
        Instrument sineWave = new SineInst();
        // save it as a file
        writeAudio(score, "Arpeggio.au", sineWave);
        //Play.au("Arpeggio.au");
        viewAudio("Arpeggio.au");
    }
}
