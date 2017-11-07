package com.garenhart.music.makingmusicwithjava;

import jm.audio.Instrument;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Write;

import java.io.File;
import java.util.Date;

public class HelperIO {

    public static String generateUniqueFileName() {
        String filename;
        long millis = System.currentTimeMillis();
        String datetime = new Date().toString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        filename = "Journey_GH_" + datetime + ".wav";
        return filename;
    }

    public static File createOutputDirectory() {
        // Create "data/out" subdirectory if doesn't exist
        File dir = new File("data/out");

        // mkdir returns false if the directory exists,
        // but for our purpose we ignore the return
        // and return the path in any case.
        dir.mkdir();
        return dir;
    }

    public static void writeXml(Score score, String fileName) {
        // Create "data/out" subdirectory if doesn't exist
        File dir = createOutputDirectory();

        Write.xml(score, dir.getPath() + "/" + fileName);
    }

    public static void writeMidi(Score score, String fileName) {
        // Create "data/out" subdirectory if doesn't exist
        File dir = createOutputDirectory();

        Write.midi(score, dir.getPath() + "/" + fileName);
    }

    public static void writeMidi(Part part, String fileName) {
        // Create "data/out" subdirectory if doesn't exist
        File dir = createOutputDirectory();

        Write.midi(part, dir.getPath() + "/" + fileName);
    }

    public static void writeAudio(float[] sampleData, String fileName) {
        // Create "data/out" subdirectory if doesn't exist
        File dir = createOutputDirectory();

        Write.audio(sampleData, dir.getPath() + "/" + fileName);
    }

    public static void writeAudio(float[] sampleData, String fileName, int channels,
                                  int sampleRate, int sampleSizeInBits) {
        // Create "data/out" subdirectory if doesn't exist
        File dir = createOutputDirectory();

        Write.audio(sampleData, dir.getPath() + "/" + fileName, channels, sampleRate, sampleSizeInBits);

    }

    public static void writeAudio(Score s, String fileName, Instrument[] instList) {
        // Create "data/out" subdirectory if doesn't exist
        File dir = createOutputDirectory();

        Write.au(s, dir.getPath() + "/" + fileName, instList);
    }

    public static void writeAudio(Part p, String fileName, Instrument[] instList) {
        // Create "data/out" subdirectory if doesn't exist
        File dir = createOutputDirectory();

        Write.au(p, dir.getPath() + "/" + fileName, instList);
    }
}