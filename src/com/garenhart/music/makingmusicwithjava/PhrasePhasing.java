package com.garenhart.music.makingmusicwithjava;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.util.Play;

public class PhrasePhasing implements JMC {

    public static void main(String[] args) {
        Phrase phrase1 = new Phrase(0.0);

        for (int i=0; i<16; i++) {
            for (int j=0; j<12; j++) {
                Note note = new Note(C4+j*2, SIXTEENTH_NOTE);
                phrase1.addNote(note);
            }
        }

        Phrase phrase2 = phrase1.copy();
        phrase1.setTempo(130.0);
        phrase2.setTempo(131.0);

        Part guitarPart = new Part(GUITAR, 0);
        guitarPart.addPhrase(phrase1);
        guitarPart.addPhrase(phrase2);

        Play.midi(guitarPart);
    }
}
