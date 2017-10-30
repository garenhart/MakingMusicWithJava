package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

/**
 * TheGrid application.
 * Code example for the book "Making Music with Java"
 * © 2003 Andrew R. Brown
 */

public class TheGrid1 implements JMC {
    private Phrase hats;
    private Part drums;

    public static void main(String[] args) {
        TheGrid1 grid = new TheGrid1();
        grid.compose();
    }

    public TheGrid1() {
        hats = new Phrase(0.0);
        drums = new Part("Drums", 25, 9);
        drums.addPhrase(hats);
    }

    public void compose() {
        int[] hatsHits =
                {FS2, REST, FS2, REST, FS2, FS2, FS2, REST, FS2, REST, FS2, REST, FS2, FS2, FS2, FS2};
        hats.addNoteList(hatsHits, 0.25);
        writeMidi(drums, "TheGrid1.mid");
        Play.midi(drums);
    }
}