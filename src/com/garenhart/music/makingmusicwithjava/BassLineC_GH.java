package com.garenhart.music.makingmusicwithjava;

import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.rt.RTLine;

public class BassLineC_GH extends RTLine {
    private int pitch = 36;
    private Note n = new Note(pitch, 1.0);
    private int[] intervals = {0, 0, 0, 4, 7, 10, 12};
    private double panPosition = 0.5;

    /**
     * Constructor
     */
    public BassLineC_GH(Instrument[] instArray) {
        super(instArray);
    }

    /**
     * Generate the next note when requested.
     */
    // @Override
    public Note getNextNote() {
        return getNote();
    }

    /**
     * Generate the next note when requested.
     */
    // looks like this method has been replaced with abstract getNextNote,
    // so let's add getNextNote and call getNote from there.
    // This way the class will be compatible with both versions (1.6.2 and 1.6.4.1)
    public synchronized Note getNote() {
        n.setPitch(pitch + intervals[(int) (Math.random() * intervals.length)]);
        n.setDynamic((int) (Math.random() * 5 + 120));
        n.setPan(panPosition);
        n.setRhythmValue((int) (Math.random() * 2 + 1) * 0.25);
        n.setDuration(n.getRhythmValue() * 0.9);
        return n;
    }


    // added for control change
    public synchronized void externalAction(Object obj, int actionNumber) {
        if (actionNumber == 1) {
            double filterCutoff = ((Double) obj).doubleValue();
            for (int i = 0; i < inst.length; i++) {
                double[] values = {filterCutoff, -1};
                inst[i].setController(values);
            }
        }

        if (actionNumber == 2) {
            panPosition = ((Double) obj).doubleValue();
            for (int i = 0; i < inst.length; i++) {
                double[] values = {-1, panPosition};
                inst[i].setController(values);
            }
        }
    }
}
