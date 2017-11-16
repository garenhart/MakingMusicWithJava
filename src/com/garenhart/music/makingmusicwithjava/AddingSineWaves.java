package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;

import static com.garenhart.music.makingmusicwithjava.HelperIO.viewAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public class AddingSineWaves implements JMC {

    public static void main(String[] args) {
        new AddingSineWaves();
    }

    public AddingSineWaves() {

        // make the jmusic score
        Part part = new Part("Multiple Sine waves", 0);
        Score score = new Score(part);
        for (int i = 0; i < 4; i++) {
            score.addPart(makeAPart(i));
        }

        // set up audio instrument
        Instrument sine = new SineInst(44100);

        // render audio file of the score
        writeAudio(score, "AddingSineWaves.au", sine);

        // check it out
        viewAudio("AddingSineWaves.au");
    }

    public Part makeAPart(int counter) {
        Part cluster = new Part();
        double rootFrequency = Math.random() * 700 + 100;
        for (int i = 1; i < 10; i++) {
            Phrase harmonic = new Phrase((counter) * 2.0 + Math.random() * 0.02);
            Note n = new Note(i * rootFrequency, 2.0 * Math.random() + 1.0 / i,
                    (int) ((Math.random() * 90 + 30) / i));
            harmonic.addNote(n);
            cluster.addPhrase(harmonic);
        }
        return cluster;
    }
}

	
