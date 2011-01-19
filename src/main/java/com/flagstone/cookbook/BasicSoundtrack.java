package com.flagstone.cookbook;
/*
 * BasicSoundtrack.java
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
import com.flagstone.transform.MovieTag;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.util.sound.SoundFactory;

/*
 * This example shows how the SoundConstructor class can be used to generate the
 * objects used to play streaming sounds in a movie. For an example of how to
 * play a sound in response to a button being clicked see the Buttons example.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicSoundtrack sound-file file-out
 *
 * where:
 *
 *     sound-file is the path to a file containing a WAVE or MP3 format sound.
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicSoundtrack {
    public static void main(String[] args) {
        try {
            String out = args.length == 1 ? "BasicSoundtrack.swf" : args[1];
            BasicSoundtrack example = new BasicSoundtrack();
            Movie movie = new Movie();
            example.createMovie(movie, args[0]);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createMovie(Movie movie, String soundFile)
            throws DataFormatException, IOException {

        float framesPerSecond = 12.0f;

        final SoundFactory factory = new SoundFactory();
        factory.read(new File(soundFile));

        final MovieHeader header = new MovieHeader();
        header.setFrameRate(framesPerSecond);
        header.setFrameSize(new Bounds(0, 0, 8000, 8000));
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));

        /*
         * Add the header that defines the parameters for the streaming sound.
         */
        movie.add(factory.streamHeader(framesPerSecond));

        /*
         * Add a SoundStreamBlock with sufficient samples for one frame. The
         * sound data loaded "on-demand" from the underlying stream managed
         * by the SoundFactory allowing, in this case, the complete sound to be
         * played or for a limited number of frames.
         */
        MovieTag block;

        while ((block = factory.streamSound()) != null) {
            movie.add(block);
            movie.add(ShowFrame.getInstance());
        }
    }
}