package com.flagstone.cookbook;
/*
 * BasicMovieClip.java
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
import com.flagstone.transform.DoAction;
import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieHeader;
import com.flagstone.transform.MovieTag;
import com.flagstone.transform.Place2;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.action.Action;
import com.flagstone.transform.action.BasicAction;
import com.flagstone.transform.action.GotoFrame;
import com.flagstone.transform.action.SetTarget;
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
 * This example shows how to create a simple movie clip and control it from the
 * main movie.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicMovieClip file-out
 *
 * where:
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicMovieClip {
    public static void main(String[] args) {
        try {
            String out = args.length == 0 ? "BasicMovieClip.swf" : args[0];
            BasicMovieClip example = new BasicMovieClip();
            Movie movie = new Movie();
            example.createMovie(movie);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createMovie(Movie movie) {

        int uid = 1;
        int xLower = 0;
        int yLower = 0;
        int xUpper = 8000;
        int yUpper = 8000;

        float framesPerSecond = 10.0f;

        MovieHeader header = new MovieHeader();
        header.setFrameRate(framesPerSecond);
        header.setFrameSize(new Bounds(xLower, yLower, xUpper, yUpper));
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));

        /*
         * Create the movie clip. A simple rectangle is drawn and then moved
         * across the screen.
         */
        DefineShape rectangle = defineRectangle(uid++, WebPalette.RED.color());
        movie.add(rectangle);

        /*
         * The first frame of the movie clip displays the shape but does not
         * start the animation.
         */
        DoAction clipActions = new DoAction(new ArrayList<Action>());
        clipActions.add(BasicAction.STOP);

        DefineMovieClip clip = new DefineMovieClip(uid++,
                new ArrayList<MovieTag>());

        /*
         * Display the shape but don't start playing the movie clip. It will
         * be started in the final frame of the main movie - this shows that
         * the timeline of movie clip is independent of the timeline of the
         * main movie.
         */
        clip.add(Place2.show(rectangle.getIdentifier(), 1, 0, 0));
        clip.add(clipActions);
        clip.add(ShowFrame.getInstance());

        // Change the position of the shape over the next 20 frames.
        for (int i = 0; i < 20; i++) {
            clip.add(Place2.move(1, i * 40, i * 40));
            clip.add(ShowFrame.getInstance());
        }

        Place2 place = Place2.show(clip.getIdentifier(), 2, 2000, 2000);
        place.setName("clip");

        movie.add(clip);
        movie.add(place);
        movie.add(ShowFrame.getInstance());

        /*
         * In the final frame of the movie, change the context to the movie clip
         * using its name and start it. After change the context back to the
         * main timeline and stop to avoid the Flash Player looping.
         */
        DoAction actions = new DoAction(new ArrayList<Action>());
        actions.add(new SetTarget("clip"));
        actions.add(new GotoFrame(2));
        actions.add(BasicAction.PLAY);
        actions.add(new SetTarget("_root"));
        actions.add(BasicAction.STOP);

        movie.add(actions);
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