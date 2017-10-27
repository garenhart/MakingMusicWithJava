package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.*;
import jm.music.tools.Mod;
import jm.util.Play;

import static com.garenhart.music.makingmusicwithjava.HelperIO.*;


public class Arpeggiator implements JMC {
    int rootNote = C2;
    int[] pattern = {0, 4, 7, 4};
    Part part = new Part("Piano", PIANO, 1);

    public static void main(String[] args) {
	    Arpeggiator arpeggiator = new Arpeggiator();
	    arpeggiator.arpeggiate();
	    arpeggiator.saveAs();
    }

    public void arpeggiate() {
        Phrase phrase1 = new Phrase(0.0);

        for (int i=0; i<pattern.length; i++) {
            phrase1.addNote(new Note(rootNote + pattern[i], SN));
        }

        // add 3 more phrases and set start times
        Phrase phrase2 = phrase1.copy();
        phrase2.setStartTime(8.0);

        Phrase phrase3 = phrase1.copy();
        phrase3.setStartTime(12.0);

        Phrase phrase4 = phrase1.copy();
        phrase4.setStartTime(16.0);

        // Set number of repeats for each phrase
        // to fit into appropriate timing
        Mod.repeat(phrase1, 8);
        Mod.repeat(phrase2,  4);
        Mod.repeat(phrase3,  4);
        Mod.repeat(phrase4,  8);

        // Transpose phrase 2 to rootNote F2
        // Transpose phrase 3 to rootNote G2
        // Transpose phrase 4 to rootNote C3
        Mod.transpose(phrase2, 5);
        Mod.transpose(phrase3, 7);
        Mod.transpose(phrase4,  12);



        part.addPhrase(phrase1);
        part.addPhrase(phrase2);
        part.addPhrase(phrase3);
        part.addPhrase(phrase4);

    }

    public void saveAs() {
        writeMidi(part, "Arpeggiator.mid");
        Play.midi(part);
    }
}
