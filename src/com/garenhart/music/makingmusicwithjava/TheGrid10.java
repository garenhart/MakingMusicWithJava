package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.Mod;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

// Adds a volume control top each instrument in the grid.
// Added change listener to deal with slider events, import javax.swing.event.*
// Adds an additional JPanel top the layout for the grid. Uses BorderLayout for the frame.
// changed default tempo to 100

public class TheGrid10 implements JMC, ItemListener, ActionListener, ChangeListener {
    private JCheckBox[] hatTicks, snareTicks, kickTicks;
    private JButton saveBtn, playBtn, audioSavebtn, audioPlayBtn, sampleSaveBtn, samplePlayBtn;
    private JComboBox repeatMenu, tempoMenu;
    // declare a music class
    private GridMusic10 musicObject;
    // declare the GUI window frame
    private JFrame window;
    // declare the faders
    private JSlider hatVolumeSlider, snareVolumeSlider, kickVolumeSlider;
    // A panel for all the instrument grids
    private JPanel gridPanel;
    
    /**
    * The main method, where it all begins.
    */
    public static void main(String[] args) {
        new TheGrid10();
    }
    
    /**
    * Constructor. Sets up the parameters.
    */
    public TheGrid10() {
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
        hatVolumeSlider = new JSlider(JSlider.VERTICAL, 0, 127, 85);
        snareVolumeSlider = new JSlider(JSlider.VERTICAL, 0, 127, 85);
        kickVolumeSlider = new JSlider(JSlider.VERTICAL, 0, 127, 85);
        // call other methods
        musicObject = new GridMusic10();
        musicObject.compose();
        makeInterface();
    }
       
    /**
    * GUI code
    */
    private void makeInterface() {
        window = new JFrame("Drum Pattern");
        // GH: Adding default close operation,
        // otherwise the app stays active after closing the interface.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(new BorderLayout());
        JPanel windowPanel = new JPanel(new BorderLayout());
        // numbers
        JPanel numbGrid = new JPanel(new GridLayout(1,18));
        numbGrid.add(new JLabel("")); // fill label
        numbGrid.add(new JLabel("Vol", SwingConstants.CENTER));
        for (int i=1; i<17; i++) {
            numbGrid.add(new JLabel(""+i, SwingConstants.CENTER));
        }
        windowPanel.add(numbGrid, "North");
        // create a grid for each instrument
        gridPanel = new JPanel(new GridLayout(3,1));
        createGrid("Hats", hatTicks, hatVolumeSlider);
        createGrid("Snare", snareTicks, snareVolumeSlider);
        createGrid("Kick", kickTicks, kickVolumeSlider);
        windowPanel.add(gridPanel, "Center");
        // initialise the grid values
        for (int i=0; i<16; i++) {
            hatTicks[i].setSelected(musicObject.getHatsState(i));
            snareTicks[i].setSelected(musicObject.getSnareState(i));
            kickTicks[i].setSelected(musicObject.getKickState(i));
        }
        // Add the button controls
        windowPanel.add(addControlPanel(), "South"); 
        window.getContentPane().add(windowPanel, "Center");
        // provide some padding
        window.getContentPane().add(new JPanel(), "North");
        window.getContentPane().add(new JPanel(), "South");
        window.getContentPane().add(new JPanel(), "East");
        window.getContentPane().add(new JPanel(), "West");
        window.pack();
        window.setVisible(true);
    }
    
    // Makes a single new grid row
    private void createGrid(String title, JCheckBox[] boxes, JSlider vol) {
        JPanel grid = new JPanel(new GridLayout(1,18));
        grid.add(new JLabel(title));
        vol.addChangeListener(this);
        vol.setMinimumSize(new Dimension(25, 40));
        vol.setPreferredSize(new Dimension(25, 60));
        grid.add(vol);
        for (int i=0; i<16; i++) {
            JCheckBox tick = new JCheckBox();
            tick.setHorizontalAlignment(SwingConstants.CENTER);
            tick.addItemListener(this);
            grid.add(tick);
            boxes[i] = tick;
        }
        gridPanel.add(grid);
    }
    
    // Makes the GUI button and pop controller area
    private JPanel addControlPanel() {
         JPanel btnPanel = new JPanel(); //new GridLayout(2,1)
        //JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //btnPanel.add(topPanel);
        //btnPanel.add(bottomPanel);
        //  buttons
        JPanel midiPanel = new JPanel(new GridLayout(2,1));
        playBtn = new JButton("Play MIDI");
        playBtn.addActionListener(this);
        midiPanel.add(playBtn);
        saveBtn = new JButton("Save MIDI");
        saveBtn.addActionListener(this);
        midiPanel.add(saveBtn);
        btnPanel.add(midiPanel);
        JPanel audioPanel = new JPanel(new GridLayout(2,1));
        audioPlayBtn = new JButton("Play Audio");
        audioPlayBtn.addActionListener(this);
        audioPanel.add(audioPlayBtn);
        audioSavebtn = new JButton("Save Audio");
        audioSavebtn.addActionListener(this);
        audioPanel.add(audioSavebtn);
        btnPanel.add(audioPanel);
        JPanel samplePanel = new JPanel(new GridLayout(2,1));
        samplePlayBtn = new JButton("Play Samples");
        samplePlayBtn.addActionListener(this);
        samplePanel.add(samplePlayBtn);
        sampleSaveBtn = new JButton("Save Samples");
        sampleSaveBtn.addActionListener(this);
        samplePanel.add(sampleSaveBtn);
        btnPanel.add(samplePanel);
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
        tempoMenu.setSelectedIndex(2);
        btnPanel.add(new JLabel("Tempo = "));
        btnPanel.add(tempoMenu);
        btnPanel.add(new JLabel("bpm")); 
        //
        return btnPanel;
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
        if(e.getSource() == samplePlayBtn) {
            musicObject.samplePlay();
        }
        if(e.getSource() == saveBtn) {            
            musicObject.saveFile("DrumPattern10.mid");
        }
        if(e.getSource() == audioSavebtn) {            
            musicObject.saveAudioFile("DrumPattern10.au");
        }        
        if(e.getSource() == sampleSaveBtn) {            
            musicObject.saveSampleFile("DrumSamplePattern10.au");
        } 
        if(e.getSource() == tempoMenu) {
            musicObject.setTempo(new Double((String)tempoMenu.getSelectedItem()).doubleValue());
        }
        if(e.getSource() == repeatMenu) {
            musicObject.repeatMusic(Integer.parseInt((String)repeatMenu.getSelectedItem()));
        }
    }
    
    /**
    * Handle slider movements
    */
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == hatVolumeSlider) musicObject.setHatVolume(hatVolumeSlider.getValue());
        if(e.getSource() == snareVolumeSlider) musicObject.setSnareVolume(snareVolumeSlider.getValue());
        if(e.getSource() == kickVolumeSlider) musicObject.setKickVolume(kickVolumeSlider.getValue());
    }
}
