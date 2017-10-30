package com.garenhart.music.makingmusicwithjava;


import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.Mod;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;


// Add drum labels
// Add repeat and tempo menus
// Use Mod class

public class TheGrid6 extends JFrame implements JMC, ItemListener, ActionListener {
    private Phrase hats, snare, kick;
    private Part drums;
    private JCheckBox[] hatTicks, snareTicks, kickTicks;
    private JButton saveBtn, playBtn;
    private JComboBox repeatMenu, tempoMenu;

    public static void main(String[] args) {
        new TheGrid6();
    }

    /**
     * Constructor. Sets up the parameters.
     */
    public TheGrid6() {
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
    }

    /**
     * Do the real musical work
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
     * GUI code
     */
    private void makeInterface() {
        JFrame window = new JFrame("Drum pattern");
        window.getContentPane().setLayout(new GridLayout(5,1));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // numbers
        JPanel numbGrid = new JPanel(new GridLayout(1,17));
        numbGrid.setPreferredSize(new Dimension(400, 20));
        numbGrid.add(new JLabel(""));
        for (int i=1; i<17; i++) {
            numbGrid.add(new JLabel(""+i, SwingConstants.CENTER));
        }
        window.getContentPane().add(numbGrid);
        // hats
        JPanel grid = new JPanel(new GridLayout(1,17));
        grid.add(new JLabel("Hats"));
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            tick.addItemListener(this);
            tick.setHorizontalAlignment(SwingConstants.CENTER);
            if(hats.getNote(i).getPitch() != REST) tick.setSelected(true);
            grid.add(tick);
            hatTicks[i] = tick;
        }
        window.getContentPane().add(grid);
        // snare
        JPanel grid2 = new JPanel(new GridLayout(1,17));
        grid2.add(new JLabel("Snare"));
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            tick.addItemListener(this);
            tick.setHorizontalAlignment(SwingConstants.CENTER);
            if(snare.getNote(i).getPitch() != REST) tick.setSelected(true);
            grid2.add(tick);
            snareTicks[i] = tick;
        }
        window.getContentPane().add(grid2);
        // kick
        JPanel grid3 = new JPanel(new GridLayout(1,17));
        grid3.add(new JLabel("Kick"));
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            tick.addItemListener(this);
            tick.setHorizontalAlignment(SwingConstants.CENTER);
            if(kick.getNote(i).getPitch() != REST) tick.setSelected(true);
            grid3.add(tick);
            kickTicks[i] = tick;
        }
        window.getContentPane().add(grid3);

        JPanel btnPanel = new JPanel();
        //  buttons
        playBtn = new JButton("Play");
        playBtn.addActionListener(this);
        btnPanel.add(playBtn);
        saveBtn = new JButton("Save as MIDI file");
        saveBtn.addActionListener(this);
        btnPanel.add(saveBtn);
        // repeats
        repeatMenu = new JComboBox();
        repeatMenu.addItem("1");
        repeatMenu.addItem("2");
        repeatMenu.addItem("4");
        repeatMenu.addItem("8");
        repeatMenu.addItem("16");
        btnPanel.add(new JLabel("Repeats = "));
        btnPanel.add(repeatMenu);
        // tempo
        tempoMenu = new JComboBox();
        tempoMenu.addActionListener(this);
        tempoMenu.addItem("60");
        tempoMenu.addItem("80");
        tempoMenu.addItem("100");
        tempoMenu.addItem("120");
        tempoMenu.addItem("140");
        tempoMenu.addItem("180");
        btnPanel.add(new JLabel("Tempo = "));
        btnPanel.add(tempoMenu);
        btnPanel.add(new JLabel("bpm"));

        window.getContentPane().add(btnPanel);

        window.pack();
        window.setVisible(true);
    }

    /**
     * Handle the checkbox clicks
     */
    public void itemStateChanged(ItemEvent e) {
        for(int i=0; i<16; i++) {
            if(e.getSource() == hatTicks[i]) {
                if(hatTicks[i].isSelected()) hats.getNote(i).setPitch(FS2);
                else hats.getNote(i).setPitch(REST);
                return;
            } else
            if(e.getSource() == snareTicks[i]) {
                if(snareTicks[i].isSelected()) snare.getNote(i).setPitch(D2);
                else snare.getNote(i).setPitch(REST);
                return;
            } else
            if(e.getSource() == kickTicks[i]) {
                if(kickTicks[i].isSelected()) kick.getNote(i).setPitch(C2);
                else kick.getNote(i).setPitch(REST);
                return;
            }
        }
    }

    /**
     * Handle the button clicks
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playBtn) {
            Part temp = drums.copy();
            Mod.repeat(temp, Integer.parseInt((String)repeatMenu.getSelectedItem()));
            Play.midi(temp, false);
        }
        if(e.getSource() == saveBtn) saveFile();
        if(e.getSource() == tempoMenu) {
            drums.setTempo(new Double((String)tempoMenu.getSelectedItem()).doubleValue());
        }
    }

    /**
     * Write the music to a MIDI file
     */
    private void saveFile() {
        Part temp = drums.copy();
        Mod.repeat(temp, Integer.parseInt((String)repeatMenu.getSelectedItem()));
        writeMidi(temp, "DrumPattern6.mid");
    }

}