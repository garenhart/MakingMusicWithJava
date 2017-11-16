package com.garenhart.music.makingmusicwithjava;

import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static com.garenhart.music.makingmusicwithjava.HelperIO.writeAudio;
import static com.garenhart.music.makingmusicwithjava.HelperIO.writeMidi;

// This version creates several parts and adds them
// to a score. Several patterns can be used also.
// There is an accent added at quarter note intervals.
// The repeat length is modified to suite the pattern length.

public class ArpAudioMidi implements JMC {
    int[] pattern1 = {0, 3, 7, 0};
    int[] pattern2 = {0, 12, 10, 7, 0, 3, 5, 7};
    int[] pattern3 = {3, 7, 15, 19};
    int[] progression = {0, 2, 0, 5, 0};
    int repeats = 8;
    double tempo = 130.0;
    Score arpScore = new Score("Argeggio Music", tempo);
    Part arpPart1, arpPart2, arpPart3;

    public static void main(String[] args) {
        ArpAudioMidi myArp = new ArpAudioMidi();
        myArp.makeScore();
        myArp.saveAs();
    }

    public void makeScore() {
        // bass
        arpPart1 = new Part("Apreggio 1", SYNTH_BASS, 1);
        arpeggiator(arpPart1, C2, pattern1, true); // part, root, arp pattern, random?
        arpScore.addPart(arpPart1);
        // upper part
        arpPart2 = new Part("Apreggio 2", POLYSYNTH, 2);
        arpeggiator(arpPart2, C5, pattern2, false); // part, root, arp pattern, random?
        arpScore.addPart(arpPart2);
        // middle part
        arpPart3 = new Part("Apreggio 3", SYNVOX, 3);
        arpeggiator(arpPart3, C3, pattern3, true); // part, root, arp pattern, random?
        arpScore.addPart(arpPart3);
    }

    public void arpeggiator(Part arpPart, int rootNote, int[] pattern, boolean randomOrder) {
        for (int j = 0; j < progression.length; j++) {
            Phrase phr = new Phrase(j * repeats);
            for (int i = 0; i < pattern.length; i++) {
                if (randomOrder) {
                    phr.addNote(new Note(rootNote + pattern[(int) (Math.random() * pattern.length)], SIXTEENTH_NOTE));
                } else {
                    phr.addNote(new Note(rootNote + pattern[i], SIXTEENTH_NOTE));
                }
            }
            Mod.repeat(phr, repeats * 4 / pattern.length);
            Mod.transpose(phr, progression[j]);
            Mod.accents(phr, 0.5);
            arpPart.addPhrase(phr);
        }
    }

    public void saveAs() {
        writeMidi(arpScore, "Arp4.mid");
        arpPart1.setInstrument(0);
        arpPart2.setInstrument(0);
        arpPart3.setInstrument(0);
        Instrument inst = new SawLPFInstF(44100);
        writeAudio(arpScore, "ArpAudio.au", inst);
    }
}