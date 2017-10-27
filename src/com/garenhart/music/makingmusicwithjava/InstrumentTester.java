package com.garenhart.music.makingmusicwithjava;

import com.sun.rowset.internal.Row;
import jm.JMC;
import jm.audio.AOException;
import jm.audio.Instrument;
import jm.constants.Instruments;
import jm.constants.ProgramChanges;
import jm.music.data.*;
import jm.util.Play;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import javax.swing.*;

public class InstrumentTester extends JFrame implements JMC {
    private JFrame mainFrame;
    private JPanel controlPanel;

    public InstrumentTester() {
        prepareGUI();
    }

    public static void main(String[] args) {
        new InstrumentTester();
    }

    private void prepareGUI(){
        mainFrame = new JFrame("Instrument Tester");
        mainFrame.setSize(300, 500);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
/*      // This is an alternative to setDefaultCloseOperation which seams to work the same way - GH
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
*/

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        controlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainFrame.add(controlPanel);

        final DefaultListModel instrumentListModel = new DefaultListModel();

        // Populate the list model and output diagnostics
        Field instruments[] = Instruments.class.getFields();
        for (int i = 0; i < instruments.length; i++) {
            Field fld = instruments[i];
            System.out.println("name = " + fld.getName());
            System.out.println("decl class = " + fld.getDeclaringClass());
            System.out.println("type = " + fld.getType());

            int mod = fld.getModifiers();
            System.out.println("modifiers = " + Modifier.toString(mod));
            System.out.println("-----");
            String name = fld.getName();
            instrumentListModel.addElement(name);
        }

        final JList instrumentList = new JList();
        instrumentList.setModel(instrumentListModel);
        instrumentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        instrumentList.setSelectedIndex(0);
        JButton stopButton = new JButton();
        UpdateButtonText(stopButton, instruments[instrumentList.getSelectedIndex()].getName());


        instrumentList.setVisibleRowCount(10);

        JScrollPane instrumentListScrollPane = new JScrollPane(instrumentList);

        instrumentList.addListSelectionListener(e-> {
            if (instrumentList.getSelectedIndex() != -1) {
                UpdateButtonText(stopButton, instruments[instrumentList.getSelectedIndex()].getName());
                try {
                    Play(ProgramChanges.class.getDeclaredField(instruments[instrumentList.getSelectedIndex()].getName()).getInt(null));
                } catch (IllegalAccessException e10) {
                    e10.printStackTrace();
                } catch (NoSuchFieldException e10) {
                    e10.printStackTrace();
                }
            }
        });

        stopButton.addActionListener(e -> Play.stopMidi());

        controlPanel.add(instrumentListScrollPane);
        controlPanel.add(stopButton);

        //mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void UpdateButtonText(JButton playButton, String text) {
        playButton.setText("Stop " + text);
    }

    private void Play(int instrument) {
        Part part = new Part(instrument, 0);
        Phrase phrase = new Phrase(0.0);
        Score score = new Score("Row Your Boat", 120);


        int[] pitchArray = {C4, C4, C4, D4, E4, E4, D4, E4, F4, G4, C5, C5, C5,
                G4, G4, G4, E4, E4, E4, C4, C4, C4, G4, F4, E4, D4, C4};
        double[] rhythmArray = {QN, QN, QNT, ENT, QN, QNT, ENT, QNT, QT, HN,
                ENT, ENT, ENT, ENT, ENT, ENT, ENT, ENT, ENT, ENT, ENT, ENT, QNT,
                ENT, QNT, ENT, HN};

        phrase.addNoteList(pitchArray, rhythmArray);
        part.addPhrase(phrase);
        score.addPart(part);

        // This overloaded method allows to avoid holding up the program
        // while playing, if second parameter is set to false - GH
        Play.midi(score, false, false, 1, 0);
    }
}