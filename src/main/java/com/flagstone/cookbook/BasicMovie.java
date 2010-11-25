package com.flagstone.cookbook;
/*
 * BasicMovie.java
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
import com.flagstone.transform.Place;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.Color;
import com.flagstone.transform.datatype.CoordTransform;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.fillstyle.FillStyle;
import com.flagstone.transform.fillstyle.SolidFill;
import com.flagstone.transform.linestyle.LineStyle;
import com.flagstone.transform.linestyle.LineStyle1;
import com.flagstone.transform.shape.DefineShape;
import com.flagstone.transform.shape.Line;
import com.flagstone.transform.shape.Shape;
import com.flagstone.transform.shape.ShapeRecord;
import com.flagstone.transform.shape.ShapeStyle;

/*
 * This example shows some of the basic objects used to create a movie.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicMovie file-out
 *
 * where:
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicMovie {
    public static void main(String[] args) {
        try {
            String out = args.length == 0 ? "BasicMovie.swf" : args[0];
            BasicMovie example = new BasicMovie();
            Movie movie = new Movie();
            example.createMovie(movie);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMovie(Movie movie) {
        int uid = 1;

        /*
         * The movie bounds are specified in twips. The bounding rectangle is
         * defined by two points: the lower left corner and the upper right
         * corner of the rectangle that encloses the area. The bounding
         * rectangle also defines the coordinate range for the x and y axes.
         * Here the coordinates for the x and y axes range from -4000 to +4000.
         * A point with the coordinates (0,0) lies in the center of the screen.
         * If the coordinates of the corners were specified as (0,0) and (8000,
         * 8000) the size of the screen is still the same however the center of
         * the screen now lies at (4000,4000)
         */
        int xLower = -4000;
        int yLower = -4000;
        int xUpper = 4000;
        int yUpper = 4000;

        /*
         * Set the frame rate at which the movie will be played.
         */
        float framesPerSecond = 1.0f;

        /*
         * The first object in a movie IS ALWAYS the MovieHeader which sets
         * the size of the Flash Player screen and frame rate.
         */
        MovieHeader header = new MovieHeader();
        header.setFrameRate(framesPerSecond);
        header.setFrameSize(new Bounds(xLower, yLower, xUpper, yUpper));
        movie.add(header);

        /*
         * Set the movie's background colour to light blue. The background
         * colour only be set once and should be the first object added to a
         * movie. If no background colour is specified then the Flash Player
         * will set it to white.
         */
        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));

        /*
         * Define a shape to be displayed. Each object must be assigned a unique
         * identifier which is used to reference the object when adding,
         * updating or removing it from the display list. The movie object keeps
         * track of the identifier to guarantee each is unique.
         */
        final int width = 4000;
        final int height = 4000;
        final int lineWidth = 20;
        final Color lineColor = WebPalette.BLACK.color();
        final Color fillColor = WebPalette.RED.color();

        final int layer = 1;

        /*
         * Create the bounding box for the shape taking into account the
         * thickness of the border. The origin is in the centre of the shape.
         */
        Bounds bounds = new Bounds(
                -(width + lineWidth) / 2, -(height + lineWidth) / 2,
                (width + lineWidth) / 2, (height + lineWidth) / 2);

        List<LineStyle> lineStyles = new ArrayList<LineStyle>();
        List<FillStyle> fillStyles = new ArrayList<FillStyle>();

        lineStyles.add(new LineStyle1(lineWidth, lineColor));
        fillStyles.add(new SolidFill(fillColor));

        /*
         * Create the outline of the shape.
         */
        List<ShapeRecord> shapeRecords = new ArrayList<ShapeRecord>();

        shapeRecords.add(new ShapeStyle().setLineStyle(1).setFillStyle(1)
                .setMove(-width / 2, -height / 2));
        shapeRecords.add(new Line(width, 0));
        shapeRecords.add(new Line(0, height));
        shapeRecords.add(new Line(-width, 0));
        shapeRecords.add(new Line(0, -height));

        DefineShape rectangle = new DefineShape(uid++, bounds, fillStyles,
                lineStyles, new Shape(shapeRecords));

        /*
         * Add the rectangle to the movie. All shapes and objects must be
         * defined before they can be placed on the display list and rendered
         * on the Flash Player's screen.
         */
        movie.add(rectangle);

        /*
         * Place the shape on the display list.
         */
        movie.add(new Place(rectangle.getIdentifier(), layer,
                CoordTransform.translate(0, 0)));

        /*
         * Show the contents of the display list. Frames are delimited by
         * successive ShowFrame objects.
         */
        movie.add(ShowFrame.getInstance());
    }
}