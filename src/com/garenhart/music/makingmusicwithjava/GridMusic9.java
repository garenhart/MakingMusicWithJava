package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.Mod;
import jm.audio.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

// add audio play method including instrument setup
// add instruments to phrases which override the part inst.
//import jm.audio.*;

public class GridMusic9 implements JMC {
    private Phrase hats, snare, kick;
    private Part drums;
    private int repeats;
    
    public GridMusic9() {
        hats = new Phrase(0.0);
        hats.setInstrument(0);
        snare = new Phrase(0.0);
        snare.setInstrument(1);
        kick = new Phrase(0.0);
        kick.setInstrument(2);
        drums = new Part("Drums", 26, 9);
        drums.setTempo(60);
        drums.addPhrase(hats);
        drums.addPhrase(snare);
        drums.addPhrase(kick);
    }
    
    /**
    * Create the initial state of the music by filling arrays with notes and rests.
    */
    public void compose() {
         int[] hatsHits = 
            {REST, REST, FS2, REST, REST, REST, FS2, REST, REST, REST, FS2, REST, REST, REST, FS2, REST,};
        hats.addNoteList(hatsHits, SIXTEENTH_NOTE, 127);
        int[] snareHits = 
            {REST, REST, REST, REST, D2, REST, REST, REST, REST, REST, REST, REST, D2, REST, D2, REST};
        snare.addNoteList(snareHits, SIXTEENTH_NOTE, 127);
        int[] kickHits = 
            {C2, REST, REST, REST, REST, REST, REST, C2, C2, REST, REST, REST, REST, REST, REST, REST};
        kick.addNoteList(kickHits, SIXTEENTH_NOTE, 127);
    }
    
    /**
    * Specify the pitch (including as a REST) of a note in the hats array.
    * @param arrayIndex The note to be changed.
    * @param pitch The value to change it's pitch to.
    */
    public void setHatsPitch(int arrayIndex, int pitch) {
        hats.getNote(arrayIndex).setPitch(pitch);
    }
    
    /**
    * Specify the pitch (including as a REST) of a note in the snare array.
    * @param arrayIndex The note to be changed.
    * @param pitch The value to change it's pitch to.
    */
    public void setSnarePitch(int arrayIndex, int pitch) {
        snare.getNote(arrayIndex).setPitch(pitch);
    }
    
    /**
    * Specify the pitch (including as a REST) of a note in the kick array.
    * @param arrayIndex The note to be changed.
    * @param pitch The value to change it's pitch to.
    */
    public void setKickPitch(int arrayIndex, int pitch) {
        kick.getNote(arrayIndex).setPitch(pitch);
    }
    
    /**
    * Pass back weather or not the specified hat note is a rest or not.
    * @return boolean True if it is a pitched note, false if it is a rest.
    */
    public boolean getHatsState(int arrayIndex) {
        return (!hats.getNote(arrayIndex).isRest());
    }
    
    /**
    * Pass back weather or not the specified snare note is a rest or not.
    * @return boolean True if it is a pitched note, false if it is a rest.
    */
    public boolean getSnareState(int arrayIndex) {
        return (!snare.getNote(arrayIndex).isRest());
    }

    /**
    * Pass back weather or not the specified kick note is a rest or not.
    * @return boolean True if it is a pitched note, false if it is a rest.
    */
    public boolean getKickState(int arrayIndex) {
        return (!kick.getNote(arrayIndex).isRest());
    }

    public void repeatMusic(int times) {
        this.repeats = times;
    }
    
    public void setTempo(double tempo) {
        drums.setTempo(tempo);
    }
    
    /**
    * Playback the music using JavaSound internal synth sounds.
    */ 
    public void play() {
        Part temp = drums.copy();
        Mod.repeat(temp, this.repeats);
        Play.midi(temp, false);
    }
    
    
    /**
    * Save the music as a standard MIDI file.
    * @param fileName The name of the MIDI file, usually ending in .mid
    */
    public void saveFile(String fileName) {
        Part temp = drums.copy();
        Mod.repeat(temp, this.repeats);
        writeMidi(temp, fileName);
    }
    
    /**
    * Playback the music using jMusic audio.
    */ 
    public void audioPlay() {
        // set up instruments
        Instrument hats = new TR808HatsInst();
        hats.setOutput(Instrument.REALTIME);
        Instrument snare = new TR808SnareInst();
        snare.setOutput(Instrument.REALTIME);
        Instrument kick = new TR808KickInst();
        kick.setOutput(Instrument.REALTIME);
        Instrument[] kit = {hats, snare, kick};
        
        Part temp = drums.copy();
        Mod.repeat(temp, this.repeats);
        Play.audio(temp, kit);
    }
    
    
    /**
    * Save the music as an au file.
    * @param fileName The name of the  file, usually ending in .au
    */
    public void saveAudioFile(String fileName) {
        // set up instruments
        Instrument hats = new TR808HatsInst(44100);
        Instrument snare = new TR808SnareInst(44100);
        Instrument kick = new TR808KickInst(44100);
        Instrument[] kit = {hats, snare, kick};
        
        Part temp = drums.copy();
        Mod.repeat(temp, this.repeats);
        writeAudio(temp, fileName, kit);
    }
    
    /**
    * Playback the music using jMusic audio with sampled sounds.
     */ 
    public void samplePlay() {
        // set up instruments
        Instrument hats = new SampleInst("data/Hats.au");
        hats.setOutput(Instrument.REALTIME);
        Instrument snare = new SampleInst("data/Snare.au");
        snare.setOutput(Instrument.REALTIME);
        Instrument kick = new SampleInst("data/Kick.au");
        kick.setOutput(Instrument.REALTIME);
        Instrument[] kit = {hats, snare, kick};
        
        Part temp = drums.copy();
        Mod.repeat(temp, this.repeats);
        Play.audio(temp, kit);
    }
    
    
    /**
    * Save the music as an au file using sampled sounds.
     * @param fileName - The name of the  file, usually ending in .au
     */
    public void saveSampleFile(String fileName) {
        // set up instruments
        Instrument hats = new SampleInst("data/Hats.au");
        Instrument snare = new SampleInst("data/Snare.au");
        Instrument kick = new SampleInst("data/Kick.au");
        Instrument[] kit = {hats, snare, kick};
        
        Part temp = drums.copy();
        Mod.repeat(temp, this.repeats);
        writeAudio(temp, fileName, kit);
    }
    
  }
