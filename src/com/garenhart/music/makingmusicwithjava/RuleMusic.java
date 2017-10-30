package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.music.tools.Mod;
import jm.util.*;

public class RuleMusic implements JMC {
    Phrase phr = new Phrase();

    public static void main(String[] args) {
        new RuleMusic(args);
    }

    public RuleMusic(String[] args) {
        String[] sentence = {"Garen", "Hartunian"};

        for(int i = 0; i<sentence.length; i++) {
            for(int j=0; j < sentence[i].length(); j++) {
                int val = Character.getNumericValue(sentence[i].charAt(j));
                System.out.println(val);
                // pitch
                int pitch = val;
                // rhythm
                Note n = new Note(pitch, SIXTEENTH_NOTE);
                phr.addNote(n);
            }
        }

        Mod.repeat(phr, 4);

        Play.midi(phr);
    }
}