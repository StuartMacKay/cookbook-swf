package com.flagstone.cookbook;
/*
 * BasicImage.java
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
import com.flagstone.transform.Place2;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Color;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.image.ImageTag;
import com.flagstone.transform.linestyle.LineStyle1;
import com.flagstone.transform.shape.ShapeTag;
import com.flagstone.transform.util.image.ImageFactory;
import com.flagstone.transform.util.image.ImageShape;

/*
 * This example shows how image can be displayed using the ImageConstructor.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicImage image-file file-out
 *
 * where:
 *
 *     image-file is the path to a file containing either BMP, PNG or JPEG
 *     image.
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicImage {
    public static void main(String[] args) {
        try {
            String out = args.length == 1 ? "BasicImage.swf" : args[1];
            BasicImage example = new BasicImage();
            Movie movie = new Movie();
            example.createMovie(movie, args[0]);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMovie(Movie movie, String imageFile)
            throws DataFormatException, IOException {

        int uid = 1;

        /*
         * Load the image and create the definition.
         */
        final ImageFactory factory = new ImageFactory();
        factory.read(new File(imageFile));
        final ImageTag image = factory.defineImage(uid++);

        /*
         * Generate the shape that actually displays the image. The origin is
         * in the centre of the shape so the registration point for the image
         * is -width/2 -height/2 so the centre of the image and the shape
         * coincide.
         */
        final int xOrigin = -image.getWidth() / 2;
        final int yOrigin = -image.getHeight() / 2;
        final int width = 20;
        final Color color = WebPalette.BLACK.color();

        final ShapeTag shape = new ImageShape().defineShape(uid++, image,
                xOrigin, yOrigin, new LineStyle1(width, color));

        /***************************************************
         * Put all the objects together in a movie
         ***************************************************/

        MovieHeader header = new MovieHeader();
        header.setFrameRate(1.0f);
        header.setFrameSize(shape.getBounds());
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));
        movie.add(image);
        movie.add(shape);
        movie.add(Place2.show(shape.getIdentifier(), 1, 0, 0));
        movie.add(ShowFrame.getInstance());
    }
}