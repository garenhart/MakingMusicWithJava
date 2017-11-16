package com.garenhart.music.makingmusicwithjava;

import jm.audio.io.*;
import jm.audio.synth.*;
import jm.music.data.Note;
import jm.audio.Instrument;

/**
* An additive synthesis instrument implementation
* which cross fades between two different spectra.
* @author Andrew Brown
*/

public final class AddMorphInst extends Instrument{
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	/** the relative frequencies which make up this note */
	private double[] freqVals = {1.0, 3.0, 5.0, 7.0, 9.0, 1.0, 0.5699, 0.57, 
            0.92, 1.1999, 1.2, 1.7, 2.74, 3.0, 3.76, 4.07, 7.3, 9.4, 10.0};
	/** the volumes to use for each frequency */
	private double[] volVals = {1.0, 0.7, 0.5, 0.3, 0.2, 1.0, 0.8, 0.75, 0.7, 
            0.65, 0.6, 0.55, 0.5, 0.45, 0.4, 0.35, 0.3, 0.15, 0.1};
	/** The points to use in the construction of Envelopes */
        private double[] pointArray1 = {0.0, 0.0, 0.1, 1.0, 0.3, 0.4, 1.0, 0.0};
	private double[] pointArray2 = {0.0, 0.0, 0.5, 0.2, 0.8, 1.0, 1.0, 0.0};
	private double[][] points = new double[freqVals.length][pointArray1.length];
		
	/** Pan */
	private float pan;
	/** The sample Rate to use */
	private int sampleRate;
	/** The Oscillators to use for each frequency specified */
	private Oscillator[] osc;
	/** The envelope to apply to each Oscillator's output */
	private Envelope[] env;
	/** The volume to apply to each envelopes output */
	private Volume[] vol;	

	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	public AddMorphInst(int sampleRate){
		this.sampleRate = sampleRate;
		// set up envelope points
		for (int i=0; i<7; i++) {
			points[i] = pointArray1;
		}
		for (int i=7; i<freqVals.length; i++) {
			points[i] = pointArray2;
		}
	}

	//----------------------------------------------
	// Methods 
	//----------------------------------------------
	/**
	 * Initialisation method is used to build the objects that
	 * this instrument will use
	 */
	public void createChain(){
		//define the audio chain(s)
		osc = new Oscillator[freqVals.length];
		env = new Envelope[freqVals.length];
		vol = new Volume[freqVals.length];
		for(int i=0;i<freqVals.length;i++){
			osc[i] = new Oscillator(this, Oscillator.SINE_WAVE,
                            this.sampleRate, 2);
			osc[i].setFrqRatio((float)freqVals[i]); 
			env[i] = new Envelope(osc[i], points[i]);
			vol[i] = new Volume(env[i], (float)volVals[i]);
		}
		//And now the add object brings us back to one path.
		Add add = new Add(vol);
		StereoPan span = new StereoPan(add);
		SampleOut sout = new SampleOut(span);
	}
}
