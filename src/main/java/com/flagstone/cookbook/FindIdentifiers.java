package com.flagstone.cookbook;
/*
 * FindIdentifiersjava
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

import com.flagstone.transform.DefineTag;
import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieTag;
import com.flagstone.transform.ShowFrame;

/*
 * This example shows how to iterate through a movie to find the highest
 * unique identifier. Three different techniques are used:
 *
 * 1. Search all frames.
 *
 * 2. Start at the end of the movie and assume the first identifier found is
 * also the highest since identifiers are typically ordered ascendantly.
 *
 * 3. Search only the first frame. This is valid only if all the definitions
 * are added to the start of the Flash (.swf) file.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.FindIdentifiers file-in
 *
 * where:
 *
 *     file-in is the path of the Flash (.swf) files to load.
 */
public final class FindIdentifiers {

    public static void main(String[] args)
    {
        try {
            String out = args[0];
            Movie movie = new Movie();
            movie.decodeFromFile(new File(out));

            System.out.println("Searching all frames...");
            System.out.println("Highest Identifier: "
            		+ highestIdentifier(movie.getObjects()));
            System.out.println("Searching for the last identifier...");
            System.out.println("Highest Identifier: "
            		+ lastIdentifier(movie.getObjects()));
            System.out.println("Searching the first frame...");
            System.out.println("Highest Identifier: "
            		+ highestIdentifierInFirstFrame(movie.getObjects()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int highestIdentifier(final List<MovieTag>list) {
        int uid = 0;
        DefineTag definition;

        for (MovieTag object : list) {
        	if (object instanceof DefineTag) {
        		definition = (DefineTag)object;
                if (definition.getIdentifier() > uid) {
                	uid = definition.getIdentifier();
                }
            }
        }
        return uid;
    }

    public static int lastIdentifier(final List<MovieTag>list) {
        int uid = 0;
        final int count = list.size();
        MovieTag object;
        DefineTag definition;

        for (int i = 0; i < count; i++) {
        	object = list.get(i);
        	if (object instanceof DefineTag) {
        		definition = (DefineTag)object;
                uid = definition.getIdentifier();
                break;
            }
        }
        return uid;
    }

    public static int highestIdentifierInFirstFrame(final List<MovieTag>list) {
        int uid = 0;
        DefineTag definition;

        for (MovieTag object : list) {
        	if (object instanceof DefineTag) {
        		definition = (DefineTag)object;
                if (definition.getIdentifier() > uid) {
                	uid = definition.getIdentifier();
                }
            } else if (object instanceof ShowFrame) {
            	break;
            }
        }
        return uid;
    }
}
