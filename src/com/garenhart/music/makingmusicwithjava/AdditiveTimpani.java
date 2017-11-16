package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.audio.*;
import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.viewAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

/*
* A domonstration of Aditive Synthesis with the AddSynthInst.
* @author Andrew Brown
*/
public final class AdditiveTimpani implements JMC{
    // Set up overtone attributes
    private double[] freqVals = {1.0, 1.42, 1.53, 1.77, 1.94, 2.01, 2.4};
    private double[] volVals = {1.0, 0.8, 0.7, 0.6, 0.5, 0.3, 0.1};
    private double[] pointArray1 = {0.0, 0.0, 0.002, 1.0, 0.3, 0.4, 0.7, 0.05, 1.0, 0.0};
    private double[] pointArray2 = {0.0, 0.0, 0.05, 1.0, 0.2, 0.3, 0.6, 0.05, 1.0, 0.0};
    private double[] pointArray3 = {0.0, 0.0, 0.01, 1.0, 0.2, 0.2, 0.5, 0.05, 1.0, 0.0};
    private double[][] points = new double[freqVals.length][pointArray1.length];
    // Set up music attribute sets
    private int[] pitches = {D3, C3, F2, A2};
    private double[] rhythms = {2.0, 1.0, 0.5, 0.5};
   
    public static void main(String[] args){
        new AdditiveTimpani();
    }
    
    public AdditiveTimpani() {
        Score score = new Score("Timpani Score", 120);
        Part part = new Part("Timp. Part", 0);
        Phrase phr = new Phrase(0.0);
        for (int i=0; i<16; i++ ) {
            Note note = new Note(pitches[(int)(Math.random() * pitches.length)], 
                rhythms[(int)(Math.random() * rhythms.length)]);
            note.setDuration(note.getRhythmValue() * 2.5);
            phr.addNote(note);
        }
        part.addPhrase(phr);
        score.addPart(part);
        
        // Construct the double array of break point envelope
        points[0] = pointArray1;
        points[1] = pointArray2;
        for(int i=2; i<freqVals.length; i++){
            points[i] = pointArray3;
        }
        // Creat instrument, render and view score.
        Instrument inst = new AddSynthInst(44100, freqVals, volVals, points);
        writeAudio(score, "AdditiveTimpani.au", inst);
        viewAudio("AdditiveTimpani.au");
    }
}
