package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.constants.ProgramChanges;
import jm.music.data.*;
import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

/**
 * This example creates a work simmilar to Arvo Prt's Tintinnabuli style.
 * @author Andrew Brown
 * @modified by Garen Hartunian
 */

// TODO: GH: Replace all numeric values, e.g. pitches with corresponding JMC constants

public final class Arvoish_GH implements JMC{
    Score theWork = new Score("Tintinabuli", 84);
    Part melody = new Part("Melody", PIANO, 1);
    Part harmony = new Part("Harmony", PIANO, 2);
    Part bass = new Part("Bass", PIPE_ORGAN, 3);
    Part sub = new Part("Sub", PIPE_ORGAN, 4);
    Phrase voice1 = new Phrase();
    Phrase voice2 = new Phrase();
    Phrase voice3 = new Phrase();
    Phrase voice4 = new Phrase();
    // create a mode for constraining the notes
    int[] mode = {9, 11, 0, 2, 4, 5, 7}; // Aeolean scale degrees
    int[] triadic = {9, 0, 4}; //A minor triad
    //create the set of rhythmic durations to use
    double[] rhythms = {1.0, 1.0, 1.0, 2.0, 2.0, 4.0, 8.0};

    public static void main(String[] args){
        new Arvoish_GH();
    }

    public Arvoish_GH() {
        // Create melody based on specified pitch and range
        int basePitch = C5;
        createMelody(basePitch, basePitch - 12, basePitch + 12);
        createAccompaniment1();
        createAccompaniment2();
        createAccompaniment3();
        saveAndPlay();
    }

    // TODO: GH: Parametrize to use pitch as input parameter

    /**
     * Create melody based on basePitch
     *
     * @param basePitch - base pitch value
     * @param bottomPitch - lowest pitch to use in melody
     * @param topPitch - highest pitch to use in melody
     */
    private void createMelody(int basePitch, int bottomPitch, int topPitch) {
        int phraseLength = 47;
        int dynamic = FORTE;
        //Create the melody as a random walk
        int offset = 0;
        int previousOffset = 0;
        for (int i=0; i<phraseLength;) {
            // next note within the specified range
            if (basePitch > bottomPitch && basePitch < topPitch) {
                offset = (int)(Math.random() * 8 - 4);
            } else {
                if (basePitch <= bottomPitch) offset = (int)(Math.random() * 6);
                if (basePitch >= topPitch) offset = (int)(Math.random() * 6 - 6);
            }

            Note note = new Note(basePitch + offset,
                    rhythms[(int)(Math.random()*rhythms.length)], dynamic);


            // adjust scale to the basePitch
            // adjustScale(basePitch);

            // make sure the note is within the scale
            // and don't allow more than two repeated notes.
            if (note.isScale(mode) && (offset != 0 || previousOffset != 0)) {
                voice1.addNote(note);
                i++;
                basePitch += offset;
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

    // TODO: GH: Parametrize this and following methods to use Phrase (voice1) as input
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


    private void adjustScale(int basePitch) {
        for (int i=0; i<mode.length; i++) {
            mode[i] = (mode[i] + basePitch) % 12;
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
        writeMidi(theWork, "Arvoish_GH.mid");

        // display score
        View.notate(theWork);
        View.show(theWork);

        // playback
        Play.midi(theWork);
    }
}