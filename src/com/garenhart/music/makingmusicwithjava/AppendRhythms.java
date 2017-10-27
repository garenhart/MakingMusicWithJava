package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.Mod;

/**
 * A short example which generates a one octave c chromatic scale
 * and writes to a MIDI file called Scale.mid
 * @author Andrew Sorensen and Andrew Brown
 */
public final class AppendRhythms implements JMC{
    public static void main(String[] args){
        new AppendRhythms();
    }

    public AppendRhythms() {
        Score s = new Score("Score", 140.0);
        Part p = new Part("Percussion", 0, 9);

        Phrase  phr = makePattern();
        Phrase  phr2 = makePattern();
        Mod.append(phr, phr2);
        p.addPhrase(phr);
        View.show(p);

        Part p2 = new Part();
        Phrase  phr3 = makePattern();
        Phrase  phr4 = makePattern();
        Mod.append(phr3, phr4);
        p2.addPhrase(phr3);
        Mod.append(p, p2);
        s.addPart(p);
        View.show(s, 50, 50);

        Score s2 = s.copy();
        Mod.append(s, s2);
        s.setTitle("Appended score");
        View.show(s, 100, 100);
        Play.midi(s);
    }

    private Phrase makePattern() {
        Phrase  phr = new Phrase();
        int startPitch = (int)(Math.random() * 48 + 36);
        double length = EIGHTH_NOTE;
        for(int i=0;i<8; i++){
            Note n = new Note(startPitch, length);
            phr.addNote(n);
            if (Math.random() < 0.5) length = EIGHTH_NOTE;
            else {
                length = QUARTER_NOTE;
                i++;
            }
        }
        return phr;
    }
}