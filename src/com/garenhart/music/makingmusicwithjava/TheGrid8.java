package com.garenhart.music.makingmusicwithjava;

import jm.JMC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

// Add real time audio playback - add audio play btn, and methods.

public class TheGrid8 implements JMC, ItemListener, ActionListener {
    private JCheckBox[] hatTicks, snareTicks, kickTicks;
    private JButton saveBtn, playBtn, audioSavebtn, audioPlayBtn;
    private JComboBox repeatMenu, tempoMenu;
    // declare a music class
    private GridMusic8 musicObject;
    private JFrame window;

    
    public static void main(String[] args) {
        new TheGrid8();
    }
    
    /**
    * Constructor. Sets up the parameters.
    */
    public TheGrid8() {
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
        musicObject = new GridMusic8();
        musicObject.compose();
        makeInterface();
    }
       
    /**
    * GUI code
    */
    private void makeInterface() {
        window = new JFrame("Drum Pattern");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(new GridLayout(5,1));
        // numbers
        JPanel numbGrid = new JPanel(new GridLayout(1,17));
        numbGrid.add(new JLabel(""));
        for (int i=1; i<17; i++) {
            numbGrid.add(new JLabel(""+i, SwingConstants.CENTER));
        }
        window.getContentPane().add(numbGrid);
        // create a grid ofr each instrument
        createGrid("Hats", hatTicks);
        createGrid("Snare", snareTicks);
        createGrid("Kick", kickTicks);
        // initialise the grid values
        for (int i=0; i<16; i++) {
            hatTicks[i].setSelected(musicObject.getHatsState(i));
            snareTicks[i].setSelected(musicObject.getSnareState(i));
            kickTicks[i].setSelected(musicObject.getKickState(i));
        }
        // Add the button controls
        addControlPanel();        
        window.pack();
        window.setVisible(true);
    }
    
    // Makes a single new grid row
    private void createGrid(String title, JCheckBox[] boxes) {
        JPanel grid = new JPanel(new GridLayout(1,17));
        grid.add(new JLabel(title));
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            tick.setHorizontalAlignment(SwingConstants.CENTER);
            tick.addItemListener(this);
            grid.add(tick);
            boxes[i] = tick;
        }
        window.getContentPane().add(grid);
    }
    
    // Makes the GUI button and pop controller area
    private void addControlPanel() {
        JPanel btnPanel = new JPanel();
        //  buttons
        playBtn = new JButton("Play MIDI");
        playBtn.addActionListener(this);
        btnPanel.add(playBtn);
        saveBtn = new JButton("Save MIDI");
        saveBtn.addActionListener(this);
        btnPanel.add(saveBtn);
        audioPlayBtn = new JButton("Play Audio");
        audioPlayBtn.addActionListener(this);
        btnPanel.add(audioPlayBtn);
        audioSavebtn = new JButton("Save Audio");
        audioSavebtn.addActionListener(this);
        btnPanel.add(audioSavebtn);
        
        // repeats
        repeatMenu = new JComboBox();
        repeatMenu.addActionListener(this);
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
    }
    
    /**
    * Handle the checkbox clicks
    */
    public void itemStateChanged(ItemEvent e) {
        for(int i=0; i<16; i++) {
            if(e.getSource() == hatTicks[i]) {
                if(hatTicks[i].isSelected()) musicObject.setHatsPitch(i, FS2);
                else musicObject.setHatsPitch(i, REST);
                return;
            } else
            if(e.getSource() == snareTicks[i]) {
                if(snareTicks[i].isSelected()) musicObject.setSnarePitch(i, D2);
                else musicObject.setSnarePitch(i, REST);
                return;
            } else
            if(e.getSource() == kickTicks[i]) {
                if(kickTicks[i].isSelected()) musicObject.setKickPitch(i, C2);
                else musicObject.setKickPitch(i, REST);
                return;
            }
        }
    }
    
    /**
    * Handle the button clicks
    */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playBtn) {
            musicObject.play();
        }
        if(e.getSource() == audioPlayBtn) {
            musicObject.audioPlay();
        }
        if(e.getSource() == saveBtn) {            
            musicObject.saveFile("DrumPattern8.mid");
        }
        if(e.getSource() == audioSavebtn) {            
            musicObject.saveAudioFile("DrumPattern8.au");
        }        
        if(e.getSource() == tempoMenu) {
            musicObject.setTempo(new Double((String)tempoMenu.getSelectedItem()).doubleValue());
        }
        if(e.getSource() == repeatMenu) {
            musicObject.repeatMusic(Integer.parseInt((String)repeatMenu.getSelectedItem()));
        }    }
}
