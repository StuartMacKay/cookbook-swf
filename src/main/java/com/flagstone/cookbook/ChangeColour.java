package com.flagstone.cookbook;
/*
 * ChangeColour.java
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
import com.flagstone.transform.Place2;
import com.flagstone.transform.PlaceType;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.Color;
import com.flagstone.transform.datatype.ColorTransform;
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
 * This example shows how to change the colour of a shape:
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.ChangeColour file-out
 *
 * where:
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class ChangeColour {
    public static void main(String[] args) {
        try {
            String out = args.length == 0 ? "ChangeColour.swf" : args[0];
            ChangeColour example = new ChangeColour();
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

        DefineShape rectangle = defineRectangle(uid++, WebPalette.RED.color());

        float framesPerSecond = 1.0f;

        MovieHeader header = new MovieHeader();
        header.setFrameRate(framesPerSecond);
        header.setFrameSize(new Bounds(xLower, yLower, xUpper, yUpper));
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));

        movie.add(rectangle);
        movie.add(Place2.show(rectangle.getIdentifier(), 1, 4000, 4000));
        movie.add(ShowFrame.getInstance());

        /*
         * Change the colour of the rectangle. Here the terms are added to the
         * colour defined for the solid fill style of the rectangle.
         */
        movie.add(new Place2().setType(PlaceType.MODIFY).setLayer(1)
                .setColorTransform(new ColorTransform(-255, 255, 0, 0)));
        movie.add(ShowFrame.getInstance());

        /*
         * Change the colour of the rectangle by changing the saturation of the
         * colours by specifying a multiplier for each channel. The alpha
         * channel is omitted so the transparency is not changed.
         */
        movie.add(new Place2().setType(PlaceType.MODIFY).setLayer(1)
                .setColorTransform(new ColorTransform(0.7f, 1.0f, 1.0f, 1.0f)));
        movie.add(ShowFrame.getInstance());

        /*
         * Both the add and multiply transforms can be performed in a single
         * step.
         */
        movie.add(new Place2().setType(PlaceType.MODIFY).setLayer(1)
                .setColorTransform(new ColorTransform(0, 128, 128, 0,
                        0.9f, 1.0f, 1.0f, 1.0f)));
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
        final Bounds bounds = new Bounds(
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
                .setMove(-width/2, -height/2));
        shapeRecords.add(new Line(width, 0));
        shapeRecords.add(new Line(0, height));
        shapeRecords.add(new Line(-width, 0));
        shapeRecords.add(new Line(0, -height));

        DefineShape rectangle = new DefineShape(identifier, bounds, fillStyles,
                lineStyles, new Shape(shapeRecords));

        return rectangle;
    }
}