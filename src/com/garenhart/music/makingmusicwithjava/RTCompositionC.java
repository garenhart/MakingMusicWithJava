package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.audio.*;
import jm.music.rt.RTLine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

public class RTCompositionC implements JMC, ActionListener, ChangeListener {
	private RTMixer mixer;
    private BassLineC bassLine;
    private JButton goBtn, stopBtn;
    private boolean firstTime = true;
    private JSlider cutoff;
    private JLabel value;
	
	public static void main(String[] args) {
		new RTCompositionC();
	}
	
	public RTCompositionC() {
        int sampleRate = 44100;
        int channels = 2;
        RTSawLPFInstC synthBass = new RTSawLPFInstC(sampleRate, 1000, channels);
        Instrument[] insts = new Instrument[] {synthBass};
        
        bassLine = new BassLineC(insts);
        bassLine.setTempo(104);
        
        displayGUI();
    }
    
    private void displayGUI() {
		JFrame window = new JFrame("Real-Time Bass Line");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(50, 250);
		JPanel panel = new JPanel(new BorderLayout());
		window.getContentPane().add(panel);
        // start stop
		goBtn = new JButton("Start");
		goBtn.addActionListener(this);
		panel.add(goBtn, "West");
        stopBtn = new JButton("Stop");
		stopBtn.addActionListener(this);
		panel.add(stopBtn, "East");
        // filter
		cutoff = new JSlider(1, 1, 100, 10);
		cutoff.setEnabled(false);
		cutoff.addChangeListener(this);
		panel.add(cutoff, "Center");
        // filter value display
        value = new JLabel("1000");
        panel.add(value, "South");
        
		window.pack();
		window.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == goBtn) {
            if (firstTime) {
                RTLine[] lineArray = new RTLine[] {bassLine};
                mixer = new RTMixer(lineArray);	
                mixer.begin();
                firstTime = false;
                cutoff.setEnabled(true);
            } else mixer.unPause();
			goBtn.setEnabled(false);
            stopBtn.setEnabled(true);
		}
        if(e.getSource() == stopBtn) {
            mixer.pause();
            goBtn.setEnabled(true);
            stopBtn.setEnabled(false);
        }
	}
    
    public void stateChanged(ChangeEvent e){
		if (e.getSource() == cutoff) {
            value.setText("" + (cutoff.getValue() * 100));
			mixer.actionLines(new Double(cutoff.getValue() * 100.0), 1);
		}
	}
}
