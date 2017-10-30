package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.gui.helper.HelperGUI;

public final class Scatter_GH extends HelperGUI implements JMC{
    public static void main(String[] args){
        new Scatter_GH();
    }

    public Scatter_GH() {
        super();
        setVariableA(70, "Tempo"); // tempo
        setVariableB(8, "# of parts"); // number of parts
        setVariableC(2, "# of phrases"); // number of phrases per part
        setVariableD(80, "Score length"); // length of the piece
        setVariableE(30, "Notes per phrase"); // number of notes in each phrase
    }

    public Score compose() {
        Score score = new Score("Scatter Piece", variableA * 2);
        // loop through parts
        // incrementing the instrument and channel each time
        for(int i=0; i<(variableB);i++) {
            Part part = new Part("Part "+ i,(int)(Math.random() * 126),i);
            // create a couple of phrases in each part
            for(int j=0; j<variableC;j++) {
                Phrase phrase = makePhrase();
                part.addPhrase(phrase);
            }
            score.addPart(part);
        }
        // send th score back to the calling class (HelperGUI)
        return(score);
    }
    /**
     * Generate a phrase based on a random walk
     */
    private Phrase makePhrase() {
        Phrase phr = new Phrase((Math.random()*variableD) * HALF_NOTE);
        double phrasePanLocation = Math.random();
        int pitch = (int)(Math.random()*60+40);
        for(int i=0;i<variableE;i++) {
            Note note;
            do {
                pitch += (int)(Math.random()*10-5);
                if (pitch<0) pitch = 0;
                if (pitch>127) pitch = 127;
                // rhythm  probability
                double rv;
                double duration = Math.random();
                if (duration < 0.2) rv = HALF_NOTE;
                else if (duration < 0.6) rv = QUARTER_NOTE;
                else rv = EIGHTH_NOTE;
                note = new Note(pitch, rv, (int)(Math.random()*70 + 30));
            } while (!note.isScale(PENTATONIC_SCALE));
            note.setPan(phrasePanLocation);
            phr.addNote(note);
        }
        return phr;
    }
}