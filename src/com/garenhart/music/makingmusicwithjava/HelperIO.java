package com.garenhart.music.makingmusicwithjava;

import jm.music.data.*;
import jm.util.*;

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
}
