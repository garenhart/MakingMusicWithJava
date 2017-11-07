package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeXml;

/**
 * A short example which usess randomness to sound like wind chimes
 *
 * @author Andrew Brown
 */
public final class WindChimes implements JMC {
    public static void main(String[] args) {
        Score score = new Score("JMDemo - Wind Chimes");
        Part part = new Part("All Chimes", BELLS, 0);
        int[] pitchSet = {C6, F6, G5, D7};

        // create a phrase of random durations up to a semibreve in length.
        for (int i = 0; i < pitchSet.length; i++) {
            Phrase phr = new Phrase((double) i);
            for (int j = 0; j < 24; j++) {
                Note note = new Note(pitchSet[i],
                        Math.random() * 8,
                        (int) (Math.random() * 80 + 20));
                phr.addNote(note);
            }
            phr.setPan(Math.random());
            part.addPhrase(phr);
        }

        // add part to the score
        score.addPart(part);

        writeXml(score, "Wind.xml");

        // create a MIDI file of the score
        writeMidi(score, "WindChimes.mid");

        // See it and play it
        View.show(score);
        Play.midi(score);

    }
}
