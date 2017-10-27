package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

public class Beat implements JMC {
    public static void main(String[] args) {
        Phrase phrase = new Phrase(0.0) ;

        for (int i=0; i<3; i++) {
            Note e = new Note(E4, EIGHTH_NOTE_TRIPLET);
            phrase.addNote(e);
        }

        Note c = new Note(C4, HALF_NOTE);
        phrase.addNote(c);
        Rest rest = new Rest(QUARTER_NOTE);
        phrase.addRest(rest);

        for (int i= 0; i<3; i++) {
            Note d = new Note(D4, EIGHTH_NOTE_TRIPLET);
            phrase.addNote(d);
        }

        Note b = new Note(B3, HALF_NOTE);
        phrase.addNote(b);

        Play.midi(phrase);
    }
}
