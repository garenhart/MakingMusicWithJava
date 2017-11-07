package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.gui.helper.HelperGUI;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Prob;

/**
 * A short example which generates a random walk melody
 * that uses a gaussian pitch distribution.
 * @author Andrew Brown
 */
public final class GaussianWalk extends HelperGUI implements JMC{
	public static void main(String[] args){
		new GaussianWalk();
	}
	
	public GaussianWalk() {}
	
	public Score compose() {
		Score score = new Score(130.0);
		Part guitarPart = new Part("Guitar", GUITAR, 0);
		Phrase melody = new Phrase();
		int pitch = C4;
		int deviation = 12;
		
		for(int i=0;i<24;i++){
			Note note = new Note(pitch, EIGHTH_NOTE);
			melody.addNote(note);
			pitch = Prob.gaussianPitch(pitch, deviation);
		}
		
		// add together
		guitarPart.addPhrase(melody);
	    score.addPart(guitarPart);
		
		return score;
	}
}
