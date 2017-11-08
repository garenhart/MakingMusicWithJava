package com.garenhart.music.makingmusicwithjava;

import jm.music.data.*;
import jm.JMC;
import jm.util.View;

import java.awt.*;

public class HistogramAnalysis implements JMC {

    public static void main(String[] args) {
        Phrase phr = new Phrase();
        for(int i=0; i<1000; ) {
            Note n = new Note((int)(Math.random() * 127) , 
			      (int)(Math.random() * 20) * 0.25, 
			      (int)(Math.random() * 127));
            n.setPan(Math.random());
            if (n.isScale(MAJOR_SCALE)) {
                phr.addNote(n);
                i++;
            }
        }
        Score score = new Score (new Part(phr));
	View.histogram(score);
	View.histogram(score, RHYTHM, 40, 30);
	View.histogram(score, DYNAMIC, 80, 60);
	View.histogram(score, PAN, 120, 90);
	View.histogram();
    }
}