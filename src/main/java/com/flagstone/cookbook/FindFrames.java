package com.flagstone.cookbook;
/*
 * FindFrames.java
 * Cookbook
 *
 * Copyright (c) 2001-2011 Flagstone Software Ltd. All rights reserved.
 *
 * This code is distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, AND Flagstone HEREBY DISCLAIMS ALL SUCH
 * WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT OF THIRD PARTY RIGHTS.
 */

import java.io.File;
import java.util.List;

import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieTag;
import com.flagstone.transform.ShowFrame;

/*
 * This example shows how to iterate through a movie to count the number of
 * frames.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.FindFrames file-in
 *
 * where:
 *
 *     file-in is the path of the Flash (.swf) files to load.
 */
public final class FindFrames {

    public static void main(String[] args)
    {
        try {
            String out = args[0];
            Movie movie = new Movie();
            movie.decodeFromFile(new File(out));

            System.out.println("Number Of Frames: "
            		+ numberOfFrames(movie.getObjects()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int numberOfFrames(final List<MovieTag>list) {
        int count = 0;
        for (MovieTag object : list) {
            if (object instanceof ShowFrame) {
                count++;
            }
        }
        return count;
    }
}
