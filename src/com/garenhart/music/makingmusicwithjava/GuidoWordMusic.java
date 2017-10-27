package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

/**
 * b3 c4 d4 e4 f4 g4 a4 b4 c5 d5 e5 f5 g5 a5 b5 c6
 * A  E  I  O  U  A  E  I  O  U  A  E  I  O  U  A
 */

public class GuidoWordMusic implements JMC {
    Phrase phr = new Phrase();
    int[] pitchesA = {b3,g4,e5};
    int[] pitchesE = {c4,a4,f5};
    int[] pitchesI = {d4,b4,g5};
    int[] pitchesO = {e4,c5,a5};
    int[] pitchesU = {f4,d5,c6};
    int consonantCount = 1;
    int prevPitch = 72;

    public static void main(String[] args) {
        new GuidoWordMusic(args);
    }

    public GuidoWordMusic(String[] sentence) {
        for(int i = 0; i<sentence.length; i++) {
            for(int j=0; j < sentence[i].length(); j++) {
                System.out.println(sentence[i].charAt(j));
                if(sentence[i].charAt(j) == 'a') {
                    System.out.println("** A");
                    chooseNote(pitchesA);
                }
                if(sentence[i].charAt(j) == 'e') {
                    System.out.println("** E");
                    chooseNote(pitchesE);
                }
                if(sentence[i].charAt(j) == 'i') {
                    System.out.println("** I");
                    chooseNote(pitchesI);
                }
                if(sentence[i].charAt(j) == 'o') {
                    System.out.println("** O");
                    chooseNote(pitchesO);
                }
                if(sentence[i].charAt(j) == 'u') {
                    System.out.println("** U");
                    chooseNote(pitchesU);
                }
                consonantCount++;
            }
        }
        phr.setTempo(120.0);
        phr.setTitle("Text to Music");
        View.notate(phr);
        Play.midi(phr);
    }

    private void chooseNote(int[] pitches) {
        int pitch = pitches[(int)(Math.random() * pitchesA.length)];
        // ensure some voice leading
        while(Math.abs(prevPitch - pitch) > 6) {
            pitch = pitches[(int)(Math.random() * pitchesA.length)];
        }
        Note n = new Note(pitch, EIGHTH_NOTE * consonantCount);
        phr.addNote(n);
        consonantCount = 1;
        prevPitch = pitch;
    }
}


