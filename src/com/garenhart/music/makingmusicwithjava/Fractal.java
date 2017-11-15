package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.gui.helper.HelperGUI;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

/**
 * A short example which generates a chromatic fractal melody
 * and writes it to a MIDI file called fractal.mid
 * @author Andrew Brown
 * Fractal algorithm by R. F. Voss as cited in Dodge and Jerse "Computer Music" p.369.
 */
public final class Fractal extends HelperGUI implements JMC {
    private Part melody;
    private Phrase phr;
    private float sum;
    private float[] rg = new float[16];
    int k, kg, ng, threshold;
    int np = 1;
    int nbits = 1;
    int numberOFNotes = 48;
    float nr = (float)(numberOFNotes);
    
	public static void main(String[] args){
        new Fractal();
    }
        
    public Fractal() {
        initializeFractal();
    }
    
    private void initializeFractal() {        
        nr = nr/2;
        while (nr > 1) {
            nbits++;
            np = 2 * np;
            nr = nr/2;
        }
        for(kg=0; kg<nbits; kg++) {
            rg[kg] = (float)(Math.random());
        }
    }
    
    public Score compose() {
        Score score = new Score("Fractal Melody", 120);
        melody = new Part("Piano", PIANO);
        phr = new Phrase();
        Note note = new Note();
        for(k=0; k<numberOFNotes; k++) {
            // reset values
            threshold = np;
            ng = nbits;
            sum = 0;
            // compute next fractal value
            while(k%threshold != 0) {
                ng--;
                threshold = threshold / 2;
            }
            for(kg=0; kg<nbits; kg++) {
                if(kg<ng) {rg[kg]=(float)(Math.random());}
                sum += rg[kg];
            }
            // convert value to a pitch
            note = new Note((int)(sum/nbits*127), Q);
            // quantise to the desired pitch class
            while (!note.isScale(MAJOR_SCALE)) {
                note.setPitch(note.getPitch() + 1); 
            }
            phr.addNote(note);
        }
        melody.addPhrase(phr);
        score.addPart(melody);
        return score;
    }
}