package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.music.tools.Mod;

public class NPart implements JMC {
    private Part part;
    //private int[] pitches = {C4, E4, G4, A4, D4, F4, E4};
    //private double[] rhythms = {Q, Q, Q, Q, Q, Q, C};
    private int[] pitches = {C4, DS4, F4, FS4, G4, AS4, C5};
    private double[] rhythms = {TN, TN, TN, TN, TN, TN, TN};
    private double partLength = 40.0;
    private double fadeInLength = 12.0;
    private double fadeOutLength = 12.0;
    private double nPartStartTime;

    public NPart(Part p) {
        this.part = p;
        int instrument = (int)(Math.random()*127);
        p.setInstrument(instrument);
        nPartStartTime = (int)(Math.random() * 200) * SN;
        p.addPhrase(makePhrase());
    }

    private Phrase makePhrase() {
        Phrase phr = new Phrase( nPartStartTime);
        phr.addNoteList(pitches, rhythms);

        // Pan phrase completely to either left or right
        // to make it more distinct - GH
        phr.setPan((Math.random() < 0.5) ? 0 : 1);
        Mod.shuffle(phr);
        Mod.cycle(phr, partLength);
        Mod.fadeIn(phr, fadeInLength);
        Mod.fadeOut(phr, fadeOutLength);
        return phr;
    }
}
