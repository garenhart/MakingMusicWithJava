package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.midi.*;
import jm.util.View;
import jm.gui.helper.HelperGUI;

/**
 * A short example which generates a random walk melody
 * @author Andrew Brown
 */
public final class RandomWalk extends HelperGUI implements JMC{
	public static void main(String[] args){
		new RandomWalk();
	}
	
	public RandomWalk() {}
	
	public Score compose() {
		Score score = new Score(130.0);
		Part guitarPart = new Part("Guitar", GUITAR, 0);
		Phrase melody = new Phrase();
		int pitch = C4;
		int deviation = 12;
		
		for(int i=0;i<24;i++){
			Note note = new Note(pitch, EIGHTH_NOTE);
			melody.addNote(note);
			pitch += (int)(Math.random() * (2 * deviation) - deviation);
			// check range
			if (pitch < 0) pitch = 0;
			if (pitch > 127) pitch= 127;
		}
		
		// add together
		guitarPart.addPhrase(melody);
	    score.addPart(guitarPart);
		
		return score;
	}
}
