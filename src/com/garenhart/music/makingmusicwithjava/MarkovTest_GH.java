package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.gui.helper.HelperGUI;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.PhraseMatrix;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
* An elementary use of the PhraseMatrix class that
* enables a markov process.
* @author Andrew Sorensen
*/

public class MarkovTest_GH extends HelperGUI implements JMC{

    public static void main(String[] args){
        new MarkovTest_GH();
    }

    private MarkovTest_GH() {
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVariableA(2, "Pitch depth");
        setVariableB(3, "Rhythm depth");
        setVariableC(1, "Dynamic depth");
    }

    public Score compose() {
        int[] pitches = {64,62,60,62,64,64,64,62,62,62,64,67,67,64,62,
                60,62,64,64,64,64,62,62,64,62,60};
        double[] rhythms = {QN,QN,QN,QN,QN,QN,HN,QN,QN,HN,QN,QN,HN,QN,
                QN,QN,QN,QN,QN,QN,QN,QN,QN,QN,QN,HN};
        int[] dynamics = {80,70,60,70,80,80,80,70,70,70,80,100,100,80,
                70,60,70,80,80,80,80,70,70,80,70,60};
        Phrase marysLamb = new Phrase();
        marysLamb.addNoteList(pitches, rhythms, dynamics);
        int pitchDepth = variableA;
        int rhythmDepth = variableB;
        int dynamicDepth = variableC;

        PhraseMatrix matrix = new PhraseMatrix(marysLamb, pitchDepth, rhythmDepth, dynamicDepth);
        Phrase myLamb = matrix.generate(true, true, true, 20);
        Score scr = new Score("MarysLamb", 120);
        Part part = new Part();
        part.addPhrase(myLamb);
        scr.addPart(part);
        return scr;
    }
}
