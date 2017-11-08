package com.garenhart.music.makingmusicwithjava;

import jm.music.data.*;
import jm.JMC;
import jm.audio.*;
import jm.util.*;

/*
* This class introduces white noise
*/
public class WhitenoiseNote implements JMC {
	
    public static void main(String[] args) {
            new WhitenoiseNote();
    }
    
    public WhitenoiseNote() {
        // create a score
        Score score = new Score(new Part(new Phrase(new Note(60, 4.0))));
        // set up audio instrument
        Instrument wave = new NoiseInst(44100);
        
        // render audio file of the score
        Write.au(score, "WhitenoiseNote.au", wave);
        View.au("WhitenoiseNote.au");
    }
}

	
