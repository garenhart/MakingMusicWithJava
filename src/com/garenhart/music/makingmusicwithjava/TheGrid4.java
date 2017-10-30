package com.garenhart.music.makingmusicwithjava;


import jm.JMC;
import jm.music.data.*;
import jm.util.*;

import java.awt.*;
import javax.swing.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

// Add visual display for each part and buttons
// Implement local and standard lock and feel


public class TheGrid4 implements JMC {
    private Phrase hats, snare, kick;
    private Part drums;
    private JCheckBox[] hatTicks, snareTicks, kickTicks;
    private JButton playBtn, saveBtn;

    public static void main(String[] args) {
        new TheGrid4();
    }

    public TheGrid4() {
        hats = new Phrase(0.0);
        snare = new Phrase(0.0);
        kick = new Phrase(0.0);
        drums = new Part("Drums", 25, 9);
        drums.addPhrase(hats);
        drums.addPhrase(snare);
        drums.addPhrase(kick);
        // set look and feel
        String local = UIManager.getSystemLookAndFeelClassName();
        String metal = UIManager.getCrossPlatformLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(local); // use local or metal
        } catch (Exception e) {}
        // set graphic data
        hatTicks = new JCheckBox[16];
        snareTicks = new JCheckBox[16];
        kickTicks = new JCheckBox[16];
        // call other methods
        compose();
        makeInterface();
        saveFile();
        play();
    }

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

    private void makeInterface() {
        JFrame window = new JFrame("Drum pattern");
        window.getContentPane().setLayout(new GridLayout(4,1));
        // hats
        JPanel grid = new JPanel(new GridLayout(1,16));
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            if(hats.getNote(i).getPitch() != REST) tick.setSelected(true);
            grid.add(tick);
            hatTicks[i] = tick;
        }
        window.getContentPane().add(grid);
        // snare
        JPanel grid2 = new JPanel(new GridLayout(1,16));
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            if(snare.getNote(i).getPitch() != REST) tick.setSelected(true);
            grid2.add(tick);
            snareTicks[i] = tick;
        }
        window.getContentPane().add(grid2);
        // kick
        JPanel grid3 = new JPanel(new GridLayout(1,16));
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            if(kick.getNote(i).getPitch() != REST) tick.setSelected(true);
            grid3.add(tick);
            kickTicks[i] = tick;
        }
        window.getContentPane().add(grid3);
        JPanel btnPanel = new JPanel();
        //  buttons
        playBtn = new JButton("Play");
        btnPanel.add(playBtn);
        saveBtn = new JButton("Save MIDI file");
        btnPanel.add(saveBtn);
        window.getContentPane().add(btnPanel);

        window.pack();
        window.setVisible(true);
    }

    private void saveFile() {
        writeMidi(drums, "DrumPattern4.mid");
    }

    private void play() {
        Play.midi(drums, false);
    }
}