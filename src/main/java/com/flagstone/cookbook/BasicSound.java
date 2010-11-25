package com.flagstone.cookbook;
/*
 * BasicSound.java ~
 * Cookbook
 *
 * Copyright (c) 2001-2010 Flagstone Software Ltd. All rights reserved.
 *
 * This code is distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, AND Flagstone HEREBY DISCLAIMS ALL SUCH
 * WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT OF THIRD PARTY RIGHTS.
 */

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import com.flagstone.transform.Background;
import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieHeader;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.sound.DefineSound;
import com.flagstone.transform.sound.SoundInfo;
import com.flagstone.transform.sound.StartSound;
import com.flagstone.transform.util.sound.SoundFactory;

/*
 * This example shows how the SoundConstructor class can be used to generate the
 * objects used to play a sound in a movie.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicSound sound-file file-out
 *
 * where:
 *
 *     sound-file is the path to a file containing a WAVE or MP3 format sound.
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicSound {
    public static void main(String[] args) {
        try {
            String sound = args[0];
            String out = args.length == 1 ? "BasicSound.swf" : args[1];
            BasicSound example = new BasicSound();
            Movie movie = new Movie();
            example.createMovie(movie, sound);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createMovie(Movie movie, String soundFile)
            throws DataFormatException, IOException {

        float framesPerSecond = 12.0f;

        /*
         * Load the sound and create the definition.
         */
        final SoundFactory factory = new SoundFactory();
        factory.read(new File(soundFile));
        final DefineSound sound = factory.defineSound(1);

        /*
         * Calculate the time it takes to play the sound and the number of
         * frames this represents.
         */
        final float duration = ((float) sound.getSampleCount()
                / (float) sound.getRate());
        final int numberOfFrames = (int) (duration * framesPerSecond);

        /***************************************************
         * Put all the objects together in a movie
         ***************************************************/

        final MovieHeader header = new MovieHeader();
        header.setFrameRate(framesPerSecond);
        header.setFrameSize(new Bounds(0, 0, 8000, 4000));
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));
        movie.add(sound);

        /*
         * Signal the Flash Player to begin playing the sound.
         */
        movie.add(new StartSound(new SoundInfo(sound.getIdentifier(),
                SoundInfo.Mode.START, 0, null)));

        /*
         * Add enough frames to give the sound time to play.
         */
        for (int i = 0; i < numberOfFrames; i++)
            movie.add(ShowFrame.getInstance());

    }
}