package com.garenhart.music.makingmusicwithjava;

//==========================================================
//File:                 Spray.java
//Function:             Cuts up an audio file in segments and
//			positions them across the stereo spectrum.
//Author:               Andrew Brown
//Environment           Java 1.1.7 with jMusic extensions
//============================================================

import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;

import java.io.File;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;


public class Spray implements JMC {

    public static void main(String[] args) {
        new Spray();
    }

    public Spray() {
        Score score = new Score();
        Part p = new Part("Spray",0,0);
        Phrase phr = new Phrase();
        String fileName = "data/Welcome.au";
        //String fileName = "Rowboat.mid";
        //String fileName = "Journey_GH_SatJul01131432EDT2017.au";
        // get file size
        int numb = (int)(Math.random()*10)+1;
        File f = new File(fileName);
        double fileSize = (double)(f.length() - 32)/44100/2/2;

        // make instrument stuff
        Instrument[] ensemble = new Instrument[1];
        ensemble[0] = new SampleInst(fileName, FRQ[C4]);
        // set start time in file
        double st = 0.0;
        // the length of each note (audio segment)
        double dur = 0.1;
        // the start panning position (left)
        double pan = 0.0;
        // the factor which sets the number of steps
        // between panning changes
        int panSpeed = 8;

        // iterate through many notes
        for(int i=0; i<100; i++) {
            // check to see if the panning position
            //should be updated this time through
            if(i%panSpeed == 0) {
                pan = Math.sin(i/panSpeed)/2+0.5;
            }
            // create a note
            Note n = new Note(C4+(int)(Math.random()*12), dur, (int)(Math.random()*127));
            n.setPan(pan);
            n.setDuration(dur*1.3);
            // set the sample read point for this note
            n.setSampleStartTime(st);
            // add the note to the phrase
            phr.addNote(n);
            // shift the sample read point forward
            st += dur;
            // check to see if the sample read point will be past
            // the end of the sample. If so reset it.
            if ((st + dur) > fileSize) st = 0.0;
            panSpeed += (int)(Math.random()*2)-1;
            if (panSpeed < 1) panSpeed = 1;
        }
        // pack the phrase into the jMusic score structure
        p.addPhrase(phr);
        score.addPart(p);
        // display the score on screen
        View.show(score);
        // render the score as an audio file
        writeAudio(score, "TestSpray.au", ensemble);
        // writeMidi(score, "TestSpray.mid");
    }
}