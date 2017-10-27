package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

/**
 * This example creates a work simmilar to Arvo Prt's Tintinnabuli style.
 * @author Andrew Brown
 */
public final class Arvoish implements JMC{
    Score theWork = new Score("Tintinabuli", 84);
    Part melody = new Part("Melody",VOICE, 1);
    Part harmony = new Part("Harmony",PIPE_ORGAN, 2);
    Part bass = new Part("Bass",PIPE_ORGAN, 3);
    Part sub = new Part("Sub",PIPE_ORGAN, 4);
    Phrase voice1 = new Phrase();
    Phrase voice2 = new Phrase();
    Phrase voice3 = new Phrase();
    Phrase voice4 = new Phrase();
    // create a mode for constraining the notes
    int[] mode = {9, 11, 0, 2, 4, 5, 7}; //A aolean scale degrees
    int[] triadic = {9, 0, 4}; //A minor triad
    //create the set of rhythmic durations to use
    double[] rhythms = {1.0, 1.0, 1.0, 2.0, 2.0, 4.0, 8.0};

    public static void main(String[] args){
        new Arvoish();
    }

    public Arvoish() {
        createMelody();
        createAccompaniment1();
        createAccompaniment2();
        createAccompaniment3();
        saveAndPlay();
    }

    private void createMelody() {
        int phraseLength = 47;
        //start on the C above middle C
        int pitch = 72;
        int dynamic = 100;
        //Create the melody as a random walk
        int offset = 0;
        int previousOffset = 0;
        for(int i=0;i<phraseLength;){
            // next note within a couple of octaves and
            // don't allow three repeated notes.
            if(pitch < 84 && pitch > 60) {
                offset = (int)(Math.random() * 8 - 4);
            } else {
                if (pitch <= 60) offset = (int)(Math.random() * 6);
                if (pitch >= 84) offset = (int)(Math.random() * 6 - 6);
            }

            //check that it is a note in the mode
            Note note = new Note(pitch + offset,
                    rhythms[(int)(Math.random()*rhythms.length)],
                    dynamic);
            if(note.isScale(mode) && (offset != 0 || previousOffset != 0)) {
                voice1.addNote(note);
                i++;
                pitch += offset;
                previousOffset = offset;
                dynamic += (int)(Math.random() * 10 - 5);
                // adjust rhythms
                if(note.isScale(new int[] {9, 4}) && note.getRhythmValue() < 8.0) {
                    note.setLength(note.getRhythmValue() * 2);
                } else if (note.getRhythmValue() > 1.0) note.setLength(note.getRhythmValue() / 2);
                if (i == phraseLength) note.setLength(8.0);
            }
        }
    }

    private void createAccompaniment1() {
        for(int i=0;i<voice1.size(); i++) {
            Note nn = voice1.getNote(i);
            //find closest harmony pitch
            Note note = new Note(nn.getPitch() - 1,
                    nn.getRhythmValue(),
                    (int)(nn.getDynamic() * 0.5));
            while(!note.isScale(triadic)) {
                note.setPitch(note.getPitch() - 1);
            }
            voice2.addNote(note);
        }
    }

    private void createAccompaniment2() {
        for(int i=0;i<voice1.size(); i++) {
            Note nn = voice1.getNote(i);
            //find another harmony pitch some varied distance below
            Note note = new Note(nn.getPitch() - (int)(Math.random() * 7 + 7),
                    nn.getRhythmValue(),
                    (int)(nn.getDynamic() * 0.5));
            while(!note.isScale(triadic) && note.getPitch()%12 != nn.getPitch()%12) {
                note.setPitch(note.getPitch() - 1);
            }
            voice3.addNote(note);
        }
    }

    private void createAccompaniment3() {
        for(int i=0;i<voice1.size(); i++) {
            Note nn = voice1.getNote(i);
            if((nn.getPitch()%12 == 9 || nn.getPitch()%12 == 4) && nn.getRhythmValue() >= 4.0) {
                Rest r = new Rest(nn.getRhythmValue() * 0.5);
                voice4.addRest(r);
                Note note = new Note(A2,
                        nn.getRhythmValue() * 0.5,
                        (int)(nn.getDynamic() * 0.5));
                voice4.addNote(note);
            } else {
                voice4.addRest(new Rest(nn.getRhythmValue()));
            }
        }
    }

    private void saveAndPlay() {
        //add a rest to allow reverb to end
        Rest end = new Rest(8.0);
        voice1.addRest(new Rest(8.0));

        // add the phrase to an instrument and that to a score
        melody.addPhrase(voice1);
        theWork.addPart(melody);
        harmony.addPhrase(voice2);
        theWork.addPart(harmony);
        bass.addPhrase(voice3);
        theWork.addPart(bass);
        sub.addPhrase(voice4);
        theWork.addPart(sub);
        // create a MIDI file of the score
        writeMidi(theWork, "Arvoish.mid");

        // display score
        View.notate(theWork);
        View.show(theWork);

        // playback
        Play.midi(theWork);
    }
}