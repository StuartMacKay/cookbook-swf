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
import java.util.ArrayList;
import java.util.List;

import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieTag;
import com.flagstone.transform.ShowFrame;

/*
 * This example shows how to iterate through a movie find objects of a given
 * type.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.FindObjects file-in
 *
 * where:
 *
 *     file-in is the path of the Flash (.swf) files to load.
 */
public final class FindObjects {

    public static void main(String[] args)
    {
        try {
            String out = args[0];
            Movie movie = new Movie();
            movie.decodeFromFile(new File(out));

            List<MovieTag>list = new ArrayList<MovieTag>();

            withInstanceof(list, movie.getObjects());
            System.out.println(": " + list.size());

            list.clear();
            withClassName(list, movie.getObjects(), "ShowFrame");
            System.out.println(": " + list.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void withInstanceof(final List<MovieTag>list,
    		final List<MovieTag>tags) {
        for (MovieTag object : tags) {
            if (object instanceof ShowFrame) {
                list.add(object);
            }
        }
    }

    public static void withClassName(final List<MovieTag>list,
    		final List<MovieTag>tags, final String className) {
        for (MovieTag object : tags) {
            if (object.getClass().getSimpleName().equals(className)) {
                list.add(object);
            }
        }
    }
}
