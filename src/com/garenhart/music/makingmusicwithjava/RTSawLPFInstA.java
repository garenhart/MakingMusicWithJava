package com.garenhart.music.makingmusicwithjava;

import jm.audio.Instrument;
import jm.audio.io.*;
import jm.audio.synth.*;
import jm.music.data.Note;
import jm.audio.AudioObject;

/**
 * A monophonic sawtooth waveform instrument implementation
 * which includes a low pass filter.
 * @author Andrew Brown
 */

public final class RTSawLPFInstA extends Instrument{
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	private int sampleRate;
    private int filterCutoff;
    private int channels;
	private Filter filt;

	//----------------------------------------------
	// Constructor
	//----------------------------------------------
     
    /**
    *  Constructor that sets sample rate, filter cutoff frequency, and channels.
    * @param sampleRate The number of samples per second (quality)
    * @param filterCutoff The frequency above which overtones are cut
    * @param channels 1 = mono, 2 = stereo.
    */
     public RTSawLPFInstA(int sampleRate, int filterCutoff, int channels){
		this.sampleRate = sampleRate;
		this.filterCutoff = filterCutoff;
		this.channels = channels;
	}

	//----------------------------------------------
	// Methods 
	//----------------------------------------------   
	/**
	 * Initialisation method used to build the objects that
	 * this instrument will use and specify thier interconnections.
	 */
	public void createChain(){
        Oscillator wt = new Oscillator(this, Oscillator.SAWTOOTH_WAVE, this.sampleRate, this.channels);
         filt = new Filter(wt, this.filterCutoff, Filter.LOW_PASS);
        Envelope env = new Envelope(filt, 
             new double[] {0.0, 0.0, 0.05, 1.0, 0.9, 1.0, 1.0, 0.0});
        Volume vol = new Volume(env);
        StereoPan pan = new StereoPan(vol);
	}	
}

