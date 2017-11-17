package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.audio.Instrument;
import jm.audio.RTMixer;
import jm.music.rt.RTLine;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RTCompositionC_GH implements JMC, ActionListener, ChangeListener {
	private RTMixer mixer;
    private BassLineC_GH bassLine;
    private JButton goBtn, stopBtn;
    private boolean firstTime = true;
    private JSlider cutoff;
    private JSlider pan;
    private JLabel value, valuePan;

	public static void main(String[] args) {
		new RTCompositionC_GH();
	}

	public RTCompositionC_GH() {
        int sampleRate = 44100;
        int channels = 2;
        RTSawLPFInstC_GH synthBass = new RTSawLPFInstC_GH(sampleRate, 1000, channels);
        Instrument[] insts = new Instrument[] {synthBass};
        
        bassLine = new BassLineC_GH(insts);
        bassLine.setTempo(104);
        
        displayGUI();
    }
    
    private void displayGUI() {
		JFrame window = new JFrame("Real-Time Bass Line");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(50, 250);
		JPanel panel = new JPanel(new BorderLayout());
        JPanel panelPan = new JPanel(new BorderLayout());
        window.getContentPane().add(panelPan, BorderLayout.NORTH);
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

        // pan
        pan = new JSlider(0, -100, 100, 0);
        pan.setEnabled(false);
        pan.addChangeListener(this);
        panelPan.add(pan, "Center");
        // pan value display
        valuePan = new JLabel("0");
        panelPan.add(valuePan, "West");


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
                pan.setEnabled(true);
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
        if (e.getSource() == pan) {
		    // GH - map pan range to (-1, 1) which makes more sense to me
            valuePan.setText("" + pan.getValue() / 100.0);
            // GH - but acceptable pan range seem to be (0, 1) so map that way to pass the value
            mixer.actionLines((pan.getValue() + 100.0) / 200.0, 2);
        }
	}
}
