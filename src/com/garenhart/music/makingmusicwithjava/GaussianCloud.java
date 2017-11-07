package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;
import jm.music.tools.Prob;
import jm.util.Play;
import jm.util.View;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

/**
 * A short example which generates a cloud texture with 
 * a gaussian pitch distrubtion.
 * Inspired by Iannis Xenakis's 'Concert PH' composition.
 * @author Andrew Brown
 */
 
public final class GaussianCloud implements JMC {

	public static void main(String[] args){
		new GaussianCloud();
	}
	
	public GaussianCloud() {
		// set the texture
		double cloudDensity = 0.1;
		Phrase phr = new Phrase();

		// make the notes
		for(int i = 0; i < 1500; i++) {	
			int rPitch = Prob.gaussianPitch(C4, 12);
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
		Score s = new Score(p, "Gaussian Cloud");
		
		// display and enable playing and saving
		View.show(s);
		writeMidi(s, "GaussianCloud.mid");
        Play.midi(s);
	}
}
