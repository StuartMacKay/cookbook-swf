package com.flagstone.cookbook;
/*
 * BasicLevels.java
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
import java.util.ArrayList;

import com.flagstone.transform.DoAction;
import com.flagstone.transform.Movie;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.action.Action;
import com.flagstone.transform.action.GetUrl;
import com.flagstone.transform.action.GetUrl2;
import com.flagstone.transform.action.Push;

/*
 * This example shows some of the basic concepts used when loading movie clips
 * onto different levels in a movie.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicLevels file-out
 *
 * where:
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 *
 * NOTE: This example uses movies included in the Cookbook release to make it
 * easier to set different targets where the movie clips should be loaded. The
 * paths are hard-wired to keep the code simple so make sure you are in the root
 * directory of the cookbook code before running the example.
 */
public class BasicLevels {
    public static void main(String[] args) {
        try {
            String out = args.length == 0 ? "BasicLevels.swf" : args[0];
            BasicLevels example = new BasicLevels();

            // Load an existing movie and add the actions to load
            // the movie clips.
            Movie movie = new Movie();
            movie.decodeFromFile(new File("src/test/resources/BasicLevels.swf"));

            example.createMovie(movie);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMovie(Movie movie) {
        // The following code loads two predefined movie clips into the
        // main movie. You can also set the target to be a web page or
        // frame.

        DoAction load1 = new DoAction(new ArrayList<Action>());

        // Movie clips can be loaded with the GetUrl action with the
        // URL to load and the target level specified in the action

        load1.add(new GetUrl("src/test/resources/BasicLevels1.swf", "_level1"));

        movie.add(load1);
        movie.add(ShowFrame.getInstance());

        // Allow sufficient time for the movie clip to play.

        for (int i = 0; i < 5; i++) {
            movie.add(ShowFrame.getInstance());
        }

        DoAction load0 = new DoAction(new ArrayList<Action>());

        // GetUrl2 is more flexible than GetUrl as it uses values for the
        // URL and target from the Flash Player's stack. In addition to loading
        // files you can also post variable values to a server at the same time.
        // See the class documentation for more information.

        load0.add(new Push.Builder().add("src/test/resources/BasicLevels2.swf").build());
        load0.add(new Push.Builder().add("_level0").build());
        load0.add(new GetUrl2(GetUrl2.Request.MOVIE_TO_TARGET));

        movie.add(load0);
        movie.add(ShowFrame.getInstance());
    }
}