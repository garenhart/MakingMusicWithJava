package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.music.tools.Mod;
import jm.gui.helper.HelperGUI;

/**
 * This class demonstrates how even random melodies
 * can benefit from higher order structure.
 * @author Andrew Brown
 */

public class MelodicStructure extends HelperGUI implements JMC {;
    Phrase phr;
    int pitch;

    public static void main(String[] args) {;
        new MelodicStructure();
    }

    public MelodicStructure() {}

    public Score compose() {
        Phrase a = makePhrase(4.0);
        Phrase b = makePhrase(4.0);
        Phrase c = makePhrase(4.0);
        Phrase d = makePhrase(8.0);
        Phrase a1 = a.copy();
        Phrase a2 = a.copy();
        Phrase c1 = c.copy();

        Part part = new Part();
        Score score = new Score (130);
        // create the structure
        part.addPhrase(a);
        part.addPhrase(b);
        part.addPhrase(a1);
        part.addPhrase(c);
        part.addPhrase(d);
        part.addPhrase(a2);
        part.addPhrase(c1);

        score.addPart(part);
        return score;
    }

    /**
     * Generate a phrase based on a random walk
     */
    private Phrase makePhrase(double beats) {
        Phrase phr = new Phrase();
        int pitch = (int)(Math.random()* 24 + 48);
        for(int i = 0; i < beats * 2; i++) {
            Note note;
            do {
                pitch += (int)(Math.random() * 10 - 5);
                if (pitch < 0) pitch = 0;
                if (pitch > 127) pitch = 127;
                note = new Note(pitch, EN, (int)(Math.random() * 70 + 30));
            } while (!note.isScale(PENTATONIC_SCALE));
            phr.addNote(note);
        }
        Mod.accents(phr, 2.0);
        return phr;
    }
}