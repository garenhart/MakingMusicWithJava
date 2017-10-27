package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.gui.helper.HelperGUI;

/**
 * @author Andrew Brown
 */
public final class HelperTest extends HelperGUI implements JMC{
    public static void main(String[] args){
        new HelperTest();
    }

    public HelperTest() {
        super();
        setVariableA(60, "Root pitch");
    }

    public Score compose() {
        Score score = new Score("Helper Test", 120.0);
        Part xylophonePart = new Part("Xylophone", XYLOPHONE, 0);
        Phrase scale = new Phrase("Scale", 0.0);

        // Create the scale phrase note by note
        // starting on the pitch in variableA
        for(int i=0;i<12;i++){
            Note n = new Note(variableA + i, EIGHTH_NOTE);
            scale.addNote(n);
        }

        xylophonePart.addPhrase(scale);
        score.addPart(xylophonePart);

        return score;
    }
}