package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.Mod;
import jm.audio.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

// Added setHatVolume etc. fields and methods
// Change default tempo to 100
// Extend notes on sample playback with Mod.fillRests

public class GridMusic10 implements JMC {
    private Phrase hats, snare, kick;
    private Part drums;
    private int repeats;
    
    public GridMusic10() {
        hats = new Phrase(0.0);
        hats.setInstrument(0);
        snare = new Phrase(0.0);
        snare.setInstrument(1);
        kick = new Phrase(0.0);
        kick.setInstrument(2);
        drums = new Part("Drums", 26, 9);
        drums.setTempo(100);
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
        hats.addNoteList(hatsHits, SIXTEENTH_NOTE);
        int[] snareHits = 
            {REST, REST, REST, REST, D2, REST, REST, REST, REST, REST, REST, REST, D2, REST, D2, REST};
        snare.addNoteList(snareHits, SIXTEENTH_NOTE);
        int[] kickHits = 
            {C2, REST, REST, REST, REST, REST, REST, C2, C2, REST, REST, REST, REST, REST, REST, REST};
        kick.addNoteList(kickHits, SIXTEENTH_NOTE);
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
    * Save the music as a standard MIDI file.
    * @param fileName The name of the MIDI file, usually ending in .mid
    */
    public void saveFile(String fileName) {
        writeMidi(updatedPart(), fileName);
    }
    
    /**
    * Playback the music using JavaSound internal synth sounds.
    */ 
    public void play() {
        Play.midi(updatedPart(), false);
    }
    /**
    * Specify the new volume for the hats
    */
    public void setHatVolume(int val) {
        // GH: Substitute setDynamic for setVolume, because setVolume doesn't exist
        // in the latest version of jMusic library (1.6.4.1)
        hats.setDynamic(val);
        // hats.setVolume(val);
    }
    
     /**
    * Specify the new volume for the snare drum
    */
    public void setSnareVolume(int val) {
        // GH: Substitute setDynamic for setVolume, because setVolume doesn't exist
        // in the latest version of jMusic library (1.6.4.1)
        snare.setDynamic(val);
        // snare.setVolume(val);
    }
    
     /**
    * Specify the new volume for the kick drum
    */
    public void setKickVolume(int val) {
        // GH: Substitute setDynamic for setVolume, because setVolume doesn't exist
        // in the latest version of jMusic library (1.6.4.1)
        kick.setDynamic(val);
        // kick.setVolume(val);
    }
    
    /**
    * Return a part with the necessary modification ready for playback.
     */
    private Part updatedPart() {
        Part temp = drums.copy();
        Mod.repeat(temp, this.repeats);

/*       GH: Commenting out this loop because getVolume is not supported
                in latest jMusic library (1.6.4.1)

        // apply volume to note dynamic for each phrase
        for(int i=0; i<temp.size();i++) {
            Phrase phr = temp.getPhrase(i);
            phr.setDynamic(phr.getVolume());
        }
*/
        return temp;
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
        Play.audio(updatedPart(), kit);
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

        writeAudio(updatedPart(), fileName, kit);
    }   
    
    /**
        * Playback the music using jMusic audio with sampled sounds.
     */ 
    public void samplePlay() {
        // set up instruments
        Instrument hats = new SampleInst("blah.aif"); //Hats.au");
        hats.setOutput(Instrument.REALTIME);
        Instrument snare = new SampleInst("Snare.au");
        snare.setOutput(Instrument.REALTIME);
        Instrument kick = new SampleInst("Kick.au");
        kick.setOutput(Instrument.REALTIME);
        Instrument[] kit = {hats, snare, kick};
        // extend notes to occupy rests
        Part p = updatedPart();
        Mod.fillRests(p);
        Play.audio(p, kit);
    }
    
    
    /**
        * Save the music as an au file using sampled sounds.
     * @param fileName - The name of the  file, usually ending in .au
     */
    public void saveSampleFile(String fileName) {
        // set up instruments
        Instrument hats = new SampleInst("Hats.au");
        Instrument snare = new SampleInst("Snare.au");
        Instrument kick = new SampleInst("Kick.au");
        Instrument[] kit = {hats, snare, kick};

        writeAudio(updatedPart(), fileName, kit);
    }
  }
