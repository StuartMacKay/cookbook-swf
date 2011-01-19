package com.flagstone.cookbook;
/*
 * FindLayers.java
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
import com.flagstone.transform.Place;
import com.flagstone.transform.Place2;
import com.flagstone.transform.Place3;

/*
 * This example shows how to iterate through a movie to find the highest layer
 * number used on the display list.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.FindLayers file-in
 *
 * where:
 *
 *     file-in is the path of the Flash (.swf) files to load.
 */
public final class FindLayers {

    public static void main(String[] args)
    {
        try {
            String out = args[0];
            Movie movie = new Movie();
            movie.decodeFromFile(new File(out));

            System.out.println("Highest Layer Number "
            		+ highestLayer(movie.getObjects()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int highestLayer(final List<MovieTag>list) {
        int layer = 0;
        Place place;
        Place2 place2;
        Place3 place3;

        for (MovieTag object : list) {
            if (object instanceof Place) {
                place = (Place)object;
                if (place.getLayer() > layer) {
                    layer = place.getLayer();
                }
            } else if (object instanceof Place2) {
                place2 = (Place2)object;
                if (place2.getLayer() > layer) {
                    layer = place2.getLayer();
                }
            } else if (object instanceof Place3) {
                place3 = (Place3)object;
                if (place3.getLayer() > layer) {
                    layer = place3.getLayer();
                }
            }
        }
        return layer;
    }
}
