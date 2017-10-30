package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

public class TheGrid2 implements JMC {
    private Phrase hats, snare, kick;
    private Part drums;

    public static void main(String[] args) {
        TheGrid2 grid = new TheGrid2();
        grid.compose();
        grid.output();
    }

    public TheGrid2() {
        hats = new Phrase(0.0);
        snare = new Phrase(0.0);
        kick = new Phrase(0.0);
        drums = new Part("Drums", 25, 9);
        drums.addPhrase(hats);
        drums.addPhrase(snare);
        drums.addPhrase(kick);
    }

    public void compose() {
        int[] hatsHits = {REST, REST, FS2, REST, REST, REST, FS2, REST, REST, REST, FS2, REST, REST, REST, FS2, REST,};
        hats.addNoteList(hatsHits, SIXTEENTH_NOTE);
        int[] snareHits = {REST, REST, REST, REST, D2, REST, REST, REST, REST, REST, REST, REST, D2, REST, D2, REST};
        snare.addNoteList(snareHits, SIXTEENTH_NOTE);
        int[] kickHits = {C2, REST, REST, REST, REST, REST, REST, C2, C2, REST, REST, REST, REST, REST, REST, REST};
        kick.addNoteList(kickHits, SIXTEENTH_NOTE);
    }

    public void output() {
        writeMidi(drums, "DrumPattern2.mid");
        Play.midi(drums);
    }
}