package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.View;

//@author Andrew Brown

public class FreeSketch implements JMC {
    public static void main(String[] args) {
        Score s = new Score("Free Sketch", 120);
        View.sketch(s);
    }
}     
            
