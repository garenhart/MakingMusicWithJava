package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.audio.Instrument;
import jm.audio.RTMixer;
import jm.music.rt.RTLine;

public class RTCompositionA implements JMC {
	private RTMixer mixer;
    private BassLineA bassLine;
	
	public static void main(String[] args) {
		new RTCompositionA();
	}
	
	public RTCompositionA() {
        int sampleRate = 44100;
        int channels = 2;
        RTSawLPFInstA synthBass = new RTSawLPFInstA(sampleRate, 1000, channels);
        Instrument[] insts = new Instrument[] {synthBass};
        
        bassLine = new BassLineA(insts);
        bassLine.setTempo(104);
        RTLine[] lineArray = new RTLine[] {bassLine};
        mixer = new RTMixer(lineArray);
        mixer.begin();
    }
}
