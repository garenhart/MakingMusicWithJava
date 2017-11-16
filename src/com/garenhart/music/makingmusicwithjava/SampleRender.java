package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;

import static com.garenhart.music.makingmusicwithjava.HelperIO.viewAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public class SampleRender implements JMC {
	
	public static void main(String[] args) {
	    new SampleRender();
	}
	
	private SampleRender() {
		int[] pitchArray = {C4, G3, C4, E4, F4, G4, C5, G5, G4, G3, G2, C3};
                double[] rhythmArray = {1.0, 0.25, 0.25, 0.25, 0.25, 0.5, 0.5, 0.125, 0.125, 0.25, 0.5, 2.0};
                Phrase phr = new Phrase();
                phr.addNoteList(pitchArray, rhythmArray);
		Score score = new Score(new Part (phr));

		Instrument ssi = new SimpleSampleInst("data/Hi.au", FRQ[C4]);
		writeAudio(score, "SampleRender.au", ssi);
        viewAudio("SampleRender.au");
	}
}
