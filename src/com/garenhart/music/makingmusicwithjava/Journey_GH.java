package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.View;
import jm.util.Play;
import jm.util.Write;

import java.util.Date;

import static com.garenhart.music.makingmusicwithjava.HelperIO.*;

/**
 * This class creates a collage of similar parts, each
 * of which may overlap and be rhythmically juxtaposed.
 * The phrase repetition is in a minimalist style.
 * The creation of parts is handled by the NPart class.
 */

public class Journey_GH implements JMC {
    private Score score = new Score("Journey_GH");

    public static void main(String[] args) {
        new Journey_GH();
    }

    public Journey_GH() {
        for(int i=0; i<12; i++) {
            new NPart(newPart());
        }

        writeMidi(score, generateUniqueFileName());

        View.notate(score);
        Play.midi(score);

    }

    private Part newPart() {
        Part p = new Part();
        p.setChannel(score.size());
        score.addPart(p);
        return p;
    }

}
