package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

public final class BingPart implements JMC{
    public static void main(String[] args){
        Part myPart = new Part("top", TRUMPET, 0);
        Phrase topPhrase = new Phrase(2.0);
        Note n = new Note(G4, HALF_NOTE);
        topPhrase.addNote(n);
        myPart.addPhrase(topPhrase);

        Phrase midPhrase = new Phrase(1.0);
        Note n2 = new Note(E4, DOTTED_HALF_NOTE);
        midPhrase.addNote(n2);
        myPart.addPhrase(midPhrase);

        Phrase bottomPhrase = new Phrase(0.0);
        Note n3 = new Note(C4, WHOLE_NOTE);
        bottomPhrase.addNote(n3);
        myPart.addPhrase(bottomPhrase);

        writeMidi(myPart, "BingPart.mid");

        Play.midi(myPart);
    }
}