package com.garenhart.music.makingmusicwithjava;

import jm.audio.Instrument;
import jm.audio.synth.*;

/**
 * A monophonic sawtooth waveform instrument implementation
 * which includes a low pass filter.
 *
 * @author Andrew Brown
 */

public final class RTSawLPFInstC_GH extends Instrument {
    //----------------------------------------------
    // Attributes
    //----------------------------------------------
    private int sampleRate;
    private int filterCutoff;
    private int channels;
    private Filter filt;
    private StereoPan pan;

    //----------------------------------------------
    // Constructor
    //----------------------------------------------

    /**
     * Constructor that sets sample rate, filter cutoff frequency, and channels.
     *
     * @param sampleRate   The number of samples per second (quality)
     * @param filterCutoff The frequency above which overtones are cut
     * @param channels     1 = mono, 2 = stereo.
     */
    public RTSawLPFInstC_GH(int sampleRate, int filterCutoff, int channels) {
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
    public void createChain() {
        Oscillator wt = new Oscillator(this, Oscillator.SAWTOOTH_WAVE, this.sampleRate, this.channels);
        filt = new Filter(wt, this.filterCutoff, Filter.LOW_PASS);
        Envelope env = new Envelope(filt,
                new double[]{0.0, 0.0, 0.05, 1.0, 0.9, 1.0, 1.0, 0.0});
        Volume vol = new Volume(env);
        pan = new StereoPan(vol);
    }

    /**
     * Changes the specified controller when called
     */
    public void setController(double[] controlValues) {
        if (controlValues[0] >= 0) filt.setCutOff(controlValues[0]);
        if (controlValues[1] >= 0) pan.setPan(controlValues[1]);
    }
}

