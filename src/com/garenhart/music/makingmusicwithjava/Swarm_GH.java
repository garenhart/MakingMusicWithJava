package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.Mod;

import java.lang.reflect.Array;
import java.util.Arrays;


// This is a slightly modified version of Swarm class, where phraseCount const value is used instead
// of hardcoded values to initialize the array of notes, which allows to test with any number of phrases
// by changing the value of phraseCount.
public class Swarm_GH implements JMC {
    private final int phraseCount = 4;
    private final int stepCount = 16;
    private Note[] notes = new Note[phraseCount];
    private Phrase[] phrases = new Phrase[phraseCount];
    private Part pianoPart = new Part(FLUTE, 0);

    public static void main(String[] args) {
        Swarm_GH noteCluster = new Swarm_GH();
    }

    private Swarm_GH() {
        // init
        for (int i = 0; i < phraseCount; i++) {
            notes[i] = new Note(C4, EIGHTH_NOTE);
        }

        for (int i=0; i<phrases.length; i++ ) {
            phrases[i] = new Phrase(0.0);
            phrases[i].addNote(notes[i]);
        }
        // create
        for(int i=0; i<stepCount; i++) {
            step();
            System.out.println("Completed step " + i);
        }
        for (int i=0; i<phrases.length; i++) {
            pianoPart.addPhrase(phrases[i]);
        }
        // play back
        Mod.shake(pianoPart, 40);
        View.show(pianoPart);
        Play.midi(pianoPart);
    }

    private void step() {
        int averagePitch = avPitch();
        for (int i=0; i<notes.length; i++) {
            notes[i].setPitch(nextPitch(notes[i].getPitch(), averagePitch, i));
            phrases[i].addNote(notes[i].copy());
        }
    }

    // calulate average pitch
    private int avPitch() {
        int av = 0;
        for (int i=0; i<phrases.length; i++) {
            av += notes[i].getPitch();
        }
        av /= notes.length;
        return av;
    }

    // computer the next pitch using flocking rules
    private int nextPitch(int currentPitch, int averagePitch, int noteIndex) {
        // add jitter
        currentPitch += (int)(Math.random() * 12 - 6);
        // converge
        int choiceScope = 1;
        while (Math.abs(averagePitch - currentPitch) > 5 || notInScale(currentPitch)) {
            if (currentPitch > averagePitch) currentPitch -= choiceScope++;
            else currentPitch += choiceScope++;
        }
        // avoid collision
        for(int i=0; i<notes.length; i++) {
            if (i== noteIndex) continue;
            choiceScope = 2;
            while (inChord(currentPitch, i)  || notInScale(currentPitch)) {
                if(currentPitch > averagePitch) currentPitch -= (int)(Math.random() * choiceScope++);
                else currentPitch += (int)(Math.random() * choiceScope++);
            }
        }
        return currentPitch;
    }

    // check if the pitch is the same as any other pitch this beat
    private boolean inChord(int currentPitch, int counter) {
        boolean result = false;
        for(int i=0; i<counter; i++) {
            if (currentPitch == notes[i].getPitch()) result = true;
        }
        return result;
    }


    // check if the pitch is not within the scale
    private boolean notInScale(int currentPitch) {
        int[] scale = JMC.PENTATONIC_SCALE;
        boolean result = true;
        for (int i=0; i<scale.length; i++) {
            if(currentPitch%12 == scale[i]) result = false;
        }
        return result;
    }
}