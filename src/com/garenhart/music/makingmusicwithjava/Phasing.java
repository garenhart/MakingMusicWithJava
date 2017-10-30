package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.music.tools.*;
import jm.util.*;

public final class Phasing implements JMC{
    public static void main(String[] args){
        Phrase melody = new Phrase(0.0);
        CPhrase chords = new CPhrase(0.0);

        // add melody
        int[] melPitches = {C5,D5,E5,A5,G5,D6,C6};
        double[] melRhythm = {DQN,EN,QN,QN,QN,EN,DQN};
        melody.addNoteList(melPitches, melRhythm);

        //add chords.
        int[] notePitches1 = {C3, E3, G3};
        int[] notePitches2 = {D3, F3, A2};
        int[] notePitches3 = {B2, D3, G3};
        chords.addChord(notePitches1, HALF_NOTE);
        chords.addChord(notePitches2, HALF_NOTE);
        chords.addChord(notePitches3, HALF_NOTE);

        // Put it in parts
        Part kalimba = new Part("Thumb Piano", KALIMBA, 0);
        kalimba.addPhrase(melody);
        Part rhodes = new Part("Electric Piano", EPIANO, 1);
        rhodes.addCPhrase(chords);

        // repeat to phase
        Mod.repeat(kalimba, 6);
        Mod.repeat(rhodes, 7);

        Score comp = new Score("Phasing Example", 90);
        comp.addPart(kalimba);
        comp.addPart(rhodes);

        // Listen to it
        Play.midi(comp);
    }
}