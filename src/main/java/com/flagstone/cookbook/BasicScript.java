package com.flagstone.cookbook;
/*
 * BasicScript.java
 * Cookbook
 *
 * Copyright (c) 2004-2010 Flagstone Software Ltd. All rights reserved.
 *
 * This code is distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, AND Flagstone HEREBY DISCLAIMS ALL SUCH
 * WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT OF THIRD PARTY RIGHTS.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.flagstone.transform.Background;
import com.flagstone.transform.EventHandler;
import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieHeader;
import com.flagstone.transform.MovieTag;
import com.flagstone.transform.Place2;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.action.Action;
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
import com.flagstone.translate.ASCompiler;
import com.flagstone.translate.ScriptException;

/*
 * This example shows how to use Translate to compile ActionScript to control a
 * movie clip.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicScript script-file file-out
 *
 * where:
 *
 *     script-file is a file containing the ActionScript that will be executed
 *     to control the movie clip.
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 *
 * IMPORTANT: When running the movie generated by the example in Internet
 * Explorer, you will need to click on the browser window so the Flash plug-in
 * gets the keyboard focus.
 */
public class BasicScript {
    public static void main(String[] args) {
        try {
            String out = args.length == 1 ? "BasicScript.swf" : args[1];
            BasicScript example = new BasicScript();
            Movie movie = new Movie();
            example.createMovie(movie, args[0]);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMovie(Movie movie, String filename)
            throws ScriptException, IOException {
        int uid = 1;
        int screenWidth = 8000;
        int screenHeight = 8000;

        /*
         * The movie clip is the simplest possible - just a simple rectangle
         * displayed for a single frame.
         */
        DefineShape rectangle = rectangle(uid++, 2000, 2000, WebPalette.RED
                .color());

        DefineMovieClip clip = new DefineMovieClip(uid++,
                new ArrayList<MovieTag>());

        clip.add(Place2.show(rectangle.getIdentifier(), 1, 0, 0));
        clip.add(ShowFrame.getInstance());

        /*
         * Now generate the encoded array of ClipEvent objects from the script
         * and add it to the object used to place the movie clip on the screen.
         * The version number of Flash to encode to is required as the format of
         * the data structures holding the event information for a movie clip
         * changed with the releases of Flash 6 and Flash 7.
         */
        Place2 location = Place2.show(clip.getIdentifier(), 1, screenWidth / 2,
                screenHeight / 2).setName("clip");

        ASCompiler compiler = new ASCompiler();
        List<Action>handlers = compiler.compile(new File(filename));

        for (Object obj : handlers) {
            location.add((EventHandler) obj);
        }

        /*
         * Put all the objects together in a movie.
         */
        MovieHeader header = new MovieHeader();
        header.setFrameRate(12.0f);
        header.setFrameSize(new Bounds(0, 0, screenWidth, screenHeight));
        movie.add(header);
        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));
        movie.add(rectangle);
        movie.add(clip);
        movie.add(location);
        movie.add(ShowFrame.getInstance());
    }

    /*
     * Defines a simple rectangle filled with a solid colour.
     * @param identifier the unique identifier used to reference the shape
     * definition.
     * @param width the width of the rectangle in twips.
     * @param height the height of the rectangle in twips.
     * @param color the colour used to fill the contents of the rectangle.
     */
    private DefineShape rectangle(int identifier, int width, int height,
            Color color) {
        Bounds bounds = new Bounds(-width / 2, -height / 2, width / 2,
                height / 2);

        List<LineStyle> lineStyles = new ArrayList<LineStyle>();
        List<FillStyle> fillStyles = new ArrayList<FillStyle>();

        lineStyles.add(new LineStyle1(20, WebPalette.BLACK.color()));
        fillStyles.add(new SolidFill(color));

        List<ShapeRecord> shapeRecords = new ArrayList<ShapeRecord>();

        shapeRecords.add(new ShapeStyle().setLineStyle(1).setFillStyle(1)
                .setMove(-width / 2, -height / 2));
        shapeRecords.add(new Line(width, 0));
        shapeRecords.add(new Line(0, height));
        shapeRecords.add(new Line(-width, 0));
        shapeRecords.add(new Line(0, -height));

        return new DefineShape(identifier, bounds, fillStyles, lineStyles,
                new Shape(shapeRecords));
    }
}