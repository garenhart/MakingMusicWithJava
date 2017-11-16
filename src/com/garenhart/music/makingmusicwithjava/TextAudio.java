package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

import static com.garenhart.music.makingmusicwithjava.HelperIO.viewAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

/**
* Uses any file as audio data.
* @author Andrew Brown
*/

public class TextAudio implements JMC {;
	public static void main(String[] args) {
            Score s = new Score(new Part(new Phrase(new Note(A4, 4.0))));
            Instrument inst = new TextInst();
            writeAudio(s, "TextAudio.au", inst);
            viewAudio("TextAudio.au");
        }
}
