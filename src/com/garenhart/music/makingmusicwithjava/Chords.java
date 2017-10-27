package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.music.tools.*;
import jm.util.*;

public final class Chords implements JMC{
    public static void main(String[] args){
        CPhrase cphrase1 = new CPhrase("Guitar Chords", 0.0);

        //add chords.
        int[] notePitches1 = {C4, E4, G4};
        int[] notePitches2 = {C4, F4, A4};
        int[] notePitches3 = {D4, G4, B4};
        cphrase1.addChord(notePitches1, QUARTER_NOTE);
        cphrase1.addChord(notePitches2, QUARTER_NOTE);
        cphrase1.addChord(notePitches3, QUARTER_NOTE);
        cphrase1.addChord(notePitches1, QUARTER_NOTE);

        // Strum the playback of the chord
        cphrase1.flam();

        // Put it in a guitar sound
        Part part1 = new Part("Guitar", GUITAR, 0);
        part1.addCPhrase(cphrase1);

        // Listen to it
        Play.midi(part1);
    }
}