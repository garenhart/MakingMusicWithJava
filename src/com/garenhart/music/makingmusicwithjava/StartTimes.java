package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.gui.helper.HelperGUI;
import jm.music.tools.Mod;

/**
 * @author Andrew Brown
 */
public final class StartTimes extends HelperGUI implements JMC{
    public static void main(String[] args){
        new StartTimes();
    }

    public StartTimes() {
        super();
        setVariableA(4, "Mel. 2 Start Time");
    }

    public Score compose() {
        Score score = new Score("Test", 140);
        Part xylophonePart1 = new Part("Bass", BASS, 0);
        Part xylophonePart2 = new Part("Xylophone", XYLOPHONE, 1);
        Phrase melody1 = new Phrase(0.0);

        int[] pitches = {C4,D4,E4,F4,G4};
        double[] rhythm = {EN,EN,SN,EN,EN,EN,EN,SN,EN};
        for(int i=0; i<rhythm.length; i++) {
            Note n = new Note(pitches[(int)(Math.random() * pitches.length)], rhythm[i]);
            melody1.addNote(n);
        }

        Mod.repeat(melody1, 8);
        Phrase melody2 = melody1.copy();
        melody2.setStartTime(variableA * SN);

        melody1.setPan(0);
        melody2.setPan(1);

        xylophonePart1.addPhrase(melody1);
        xylophonePart2.addPhrase(melody2);

        score.addPart(xylophonePart1);
        score.addPart(xylophonePart2);

        return score;

    }
}