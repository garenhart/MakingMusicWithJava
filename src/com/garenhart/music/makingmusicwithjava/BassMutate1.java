package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

public class BassMutate1 implements JMC {
    public static void main(String[] args) {
        new BassMutate1();
    }
	
    public BassMutate1() {
        Part bassPart = new Part();
        Phrase bassLine = new Phrase(0.0);
        for (int i = 0; i<16; i++) {
            bassLine.addNote(new Note(48, 0.25));
        }
        bassPart.addPhrase(bassLine.copy());
		
        for(int i=1; i<8; i++){
            Note n = bassLine.getNote((int)(Math.random() * 
                 bassLine.size()));
            n.setPitch((int)(Math.random() * 12 + 48));
            bassLine.setStartTime(4.0 * i);
            bassPart.addPhrase(bassLine.copy());
        }
		
        View.print(bassPart);
        Play.midi(bassPart);	
    }
}