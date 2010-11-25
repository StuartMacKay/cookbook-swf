package com.flagstone.cookbook;
/*
 * BasicLayers.java
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
import java.util.List;

import com.flagstone.transform.Background;
import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieHeader;
import com.flagstone.transform.MovieTag;
import com.flagstone.transform.Place2;
import com.flagstone.transform.Remove2;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.Color;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.fillstyle.FillStyle;
import com.flagstone.transform.fillstyle.SolidFill;
import com.flagstone.transform.linestyle.LineStyle;
import com.flagstone.transform.linestyle.LineStyle1;
import com.flagstone.transform.movieclip.DefineMovieClip;
import com.flagstone.transform.shape.DefineShape;
import com.flagstone.transform.shape.Line;
import com.flagstone.transform.shape.Shape;
import com.flagstone.transform.shape.ShapeRecord;
import com.flagstone.transform.shape.ShapeStyle;

/*
 * This example shows some of the basic concepts used when displaying objects on
 * display list layers.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicLayers file-out
 *
 * where:
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicLayers {
    public static void main(String[] args) {
        try {
            String out = args.length == 0 ? "BasicLayers.swf" : args[0];
            BasicLayers example = new BasicLayers();
            Movie movie = new Movie();
            example.createMovie(movie);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMovie(Movie movie) {

        int uid = 1;

        int xLower = 0;
        int yLower = 0;
        int xUpper = 8000;
        int yUpper = 8000;

        float framesPerSecond = 1.0f;

        DefineShape red = defineRectangle(uid++, WebPalette.RED.color());
        DefineShape green = defineRectangle(uid++, WebPalette.GREEN.color());
        DefineShape blue = defineRectangle(uid++, WebPalette.BLUE.color());

        /***************************************************
         * Put all the objects together in a movie
         ***************************************************/

        MovieHeader header = new MovieHeader();
        header.setFrameSize(new Bounds(xLower, yLower, xUpper, yUpper));
        header.setFrameRate(framesPerSecond);
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));
        movie.add(red);
        movie.add(green);
        movie.add(blue);

        /*
         * Place each rectangle on a separate layer. The red rectangle is on the
         * lowest layer so it will be displayed behind the green one and the
         * blue rectangle displayed in front.
         */
        movie.add(Place2.show(red.getIdentifier(), 1, 1000, 1000));
        movie.add(Place2.show(green.getIdentifier(), 2, 2000, 2000));
        movie.add(Place2.show(blue.getIdentifier(), 3, 3000, 3000));
        movie.add(ShowFrame.getInstance());

        /*
         * Use a copy of the green rectangle to clip the blue rectangle that is
         * displayed in front of it. The blue rectangle is removed and added to
         * the display list after the clipping layer is defined to ensure that
         * it is displayed correctly.
         */
        movie.add(new Remove2(3));
        movie.add(Place2.show(green.getIdentifier(), 3, 2000, 2000)
                        .setDepth(4));
        movie.add(Place2.show(blue.getIdentifier(), 4, 3000, 3000));
        movie.add(ShowFrame.getInstance());

        /*
         * Replace the green rectangle (and the one used for clipping) with a
         * movie clip that in turn contains several layers to show that the
         * layer numbers in the main movie are independent of the layer numbers
         * used within a movie clip (or any other composite object such as a
         * button).
         */
        List<MovieTag> list = new ArrayList<MovieTag>();
        list.add(Place2.show(red.getIdentifier(), 1, 500, 2000));
        list.add(Place2.show(green.getIdentifier(), 2, 2000, 2000));
        list.add(Place2.show(blue.getIdentifier(), 3, 3500, 2000));
        list.add(ShowFrame.getInstance());

        DefineMovieClip clip = new DefineMovieClip(uid++, list);

        movie.add(clip);

        movie.add(new Remove2(2)); // remove green rectangle
        movie.add(new Remove2(3)); // remove clipping rectangle
        movie.add(new Remove2(4)); // remove blue rectangle so it gets
                                   // redisplayed

        movie.add(Place2.show(clip.getIdentifier(), 2, 0, 0));
        movie.add(Place2.show(blue.getIdentifier(), 3, 3000, 3000));
        movie.add(ShowFrame.getInstance());
    }

    private DefineShape defineRectangle(int identifier, Color fillColor) {

        final int width = 4000;
        final int height = 4000;
        final int lineWidth = 20;
        final Color lineColor = WebPalette.BLACK.color();

        /*
         * Create the bounding box for the shape taking into account the
         * thickness of the border.
         */
        final Bounds bounds = new Bounds(-lineWidth / 2, -lineWidth / 2,
                width + lineWidth / 2, height + lineWidth / 2);

        List<LineStyle> lineStyles = new ArrayList<LineStyle>();
        List<FillStyle> fillStyles = new ArrayList<FillStyle>();

        lineStyles.add(new LineStyle1(lineWidth, lineColor));
        fillStyles.add(new SolidFill(fillColor));

        /*
         * Create the outline of the shape.
         */
        List<ShapeRecord> shapeRecords = new ArrayList<ShapeRecord>();

        shapeRecords.add(new ShapeStyle().setLineStyle(1).setFillStyle(1)
                .setMove(0, 0));
        shapeRecords.add(new Line(width, 0));
        shapeRecords.add(new Line(0, height));
        shapeRecords.add(new Line(-width, 0));
        shapeRecords.add(new Line(0, -height));

        DefineShape rectangle = new DefineShape(identifier, bounds, fillStyles,
                lineStyles, new Shape(shapeRecords));

        return rectangle;
    }
}