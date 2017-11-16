package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.music.data.*;
import jm.audio.*;
import jm.music.rt.RTLine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

public class RTCompositionB implements JMC, ActionListener {
	private RTMixer mixer;
    private BassLineA bassLine;
    private JButton goBtn, stopBtn;
    private boolean firstTime = true;
	
	public static void main(String[] args) {
		new RTCompositionB();
	}
	
	public RTCompositionB() {
        int sampleRate = 44100;
        int channels = 2;
        RTSawLPFInstA synthBass = new RTSawLPFInstA(sampleRate, 1000, channels);
        Instrument[] insts = new Instrument[] {synthBass};
        
        bassLine = new BassLineA(insts);
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
}
