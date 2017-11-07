package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

/**
 * A short example which generates a random cloud texture
 * Inspired by Iannis Xenakis's 'Concert PH' composition
 * @author Andrew Brown
 */
 
public final class CloudX implements JMC {
	public static void main(String[] args){
		// set the texture
		double cloudDensity = 0.1;
		Phrase phr = new Phrase();

		// make the notes
		for(int i = 0; i < 1500; i++) {	
			int rPitch = (int)(Math.random()*127);
			double rv = Math.random() * cloudDensity;
			int rDyn = (int)(Math.random()*127);
			Note n = new Note(rPitch, rv, rDyn);
			n.setPan(Math.random());
			phr.addNote(n);
		}
		
		// add some elegance to the end
		Mod.fadeOut(phr, 20);
		
		// create jMusic score structure
		Part p = new Part("Cloud", BREATHNOISE, 0);
		p.addPhrase(phr);
		Score s = new Score(p);
		
		View.show(s);
		writeMidi(s, "CloudX.mid");
		Play.midi(s);
	}
}
