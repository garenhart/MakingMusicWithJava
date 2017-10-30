package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;
import jm.util.Play;

/**
 * A section of Steve Reich's "Piano Phase"
 * This demonstrates how jMusic can play two parts at different tempi.
 * @author Andrew Brown
 */
public class PianoPhase implements JMC {

    public static void main(String[] args) {
        Score score = new Score("Piano Phase", 120.0);
        score.setTimeSignature(12, 16);
        Part part1 = new Part("Piano1", PIANO, 0);
        Part part2 = new Part("Piano2", PIANO, 1);
        Phrase phrase1 = new Phrase(0.0);
        Phrase phrase2 = new Phrase(0.0);

        //Write the music in a convenient way.
        int[] pitchArray = {E4,FS4,B4,CS5,D5,FS4,E4,CS5,B4,FS4,D5,CS5};

        //Add the notes to the phrases
        phrase1.addNoteList(pitchArray, SIXTEENTH_NOTE);
        phrase2.addNoteList(pitchArray, SIXTEENTH_NOTE);

        //Repeat the stable piano part and add it to a part and score
        Mod.repeat(phrase1, 41);
        phrase1.setPan(0.05);
        part1.addPhrase(phrase1);
        score.addPart(part1);

        Mod.repeat(phrase2, 41);
        phrase2.setPan(0.95);
        part2.addPhrase(phrase2);
        score.addPart(part2);

        // fade out
        Mod.fadeOut(score, 4.0);

        //Set the tempo of part 2 different from the overall tempo
        part2.setTempo(120.5);

        //OK, now listen to it
        Play.midi(score);


    }
}
