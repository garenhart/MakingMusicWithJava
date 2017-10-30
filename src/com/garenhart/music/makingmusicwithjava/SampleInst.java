package com.garenhart.music.makingmusicwithjava;

import jm.audio.io.*;
import jm.audio.Instrument;
import jm.audio.synth.*;
import jm.music.data.Note;
import jm.audio.AudioObject;

public final class SampleInst extends Instrument{
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	/** the name of the sample file */
	private String fileName;
	/** How many channels is the sound file we are using */
	private int numOfChannels;
	/** the base frequency of the sample file to be read in */
	private double baseFreq;
	/** should we play the wholeFile or just what we need for note duration */
	private boolean wholeFile;
	/** The points for the break point envelope */
	private double[] points;
    /** Sample out audio object for rendering */
    SampleOut sout;
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	/**
	 * Constructor
	 */
	public SampleInst(String fileName){
		this(fileName, 440.00);
	}
	
 	public SampleInst(String fileName, double baseFreq){
        this(fileName, baseFreq, new double[] {0.0, 0.0, 0.001, 1.0, 0.95, 1.0, 1.0, 0.0});
	}
	
		
	public SampleInst(String fileName, double baseFreq, double[] points){
		this.fileName = fileName;
		this.baseFreq = baseFreq;
		this.points = points;
	}

	//----------------------------------------------
	// Methods
	//----------------------------------------------
	/**
	 * Create the Audio Chain for this Instrument 
	 * and assign the primary Audio Object(s). The 
	 * primary audio object(s) are the one or more
	 * objects which head up the chain(s)
	 */
	public void createChain(){
        //define the chain of audio objects
        SampleIn sin = new SampleIn(this, fileName, true);
        //sin.setCacheInput(true);
        Volume vol = new Volume(sin);
        Volume gain = new Volume(vol, 8.0);
        if(output == RENDER) {
            sout = new SampleOut(gain);
            sin.setWholeFile(true);	
        } else {
            Envelope env = new Envelope(gain, points);
        }
	}
}
