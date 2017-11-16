package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.audio.*;
import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.viewAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

/**
 * @author Andrew Brown
 */
 
public final class Morph implements JMC{
    public static void main(String[] args){
        new Morph();
    }
    
    public Morph() {
        Score score = new Score(new Part(new Phrase(new Note(C3, 10.0))));
        Instrument inst = new AddMorphInst(22050);
        writeAudio(score, "Morph.au", inst);
        viewAudio("Morph.au");
    }
}
