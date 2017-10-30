package com.garenhart.music.makingmusicwithjava;


import jm.JMC;
import jm.music.data.*;
import jm.util.Play;
import jm.util.Write;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

public class Recombine_Original implements JMC {
    Part structure = new Part(0, 0);

    double[] r1 = {1.0};
    double[] r2 = {0.5, 0.5};
    double[] r3 = {0.25, 0.25, 0.25};
    double[] r4 = {0.25, 0.5, 0.25};
    double[] r5 = {0.25, 0.25, 0.25, 0.25};

    double[][] rhythms = {r1, r2, r3, r4, r5};

    public static void main(String[] args) {
        Recombine_Original recombine = new Recombine_Original();
    }

    public Recombine_Original() {
        arrange();
        savePlay();
    }

    public void arrange() {
        for (int i=0; i<16; i++) {
            Phrase phrase = new Phrase();

            if (i%4 == 0) {
                phrase.addNoteList(36, rhythms[0]);
            }
            else {
                phrase.addNoteList(42, rhythms[(int)(Math.random() * rhythms.length)]);
            }

            structure.addPhrase(phrase);
        }
    }

    public void savePlay() {
        Play.midi(structure);
        writeMidi(structure,  "Recombine MMwJ.mid");
    }

}
