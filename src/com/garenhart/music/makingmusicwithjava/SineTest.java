package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;

public final class SineTest implements JMC{
    public static void main(String[] args){
        Score s = new Score();
        s.setTempo(60);
        Part p = new Part("Sine", 0);
                
        Phrase phr = new Phrase(0.0);
        for (int i=0; i<50;i++) {
            Note n = new Note(Math.random()*100+1000, 4.0);
            n.setPan(Math.random());
            Phrase phrase2  = new Phrase(n, Math.random()*30);
            p.addPhrase(phrase2);
        }
        p.addPhrase(phr);
        s.addPart(p);

        View.show(s);

        Instrument inst = new SineInst(22000);
        writeAudio(s,"SineTest.au", inst);
    }
}