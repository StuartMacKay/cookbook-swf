package com.flagstone.cookbook;
/*
 * ChangeShape.java
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
import com.flagstone.transform.datatype.CoordTransform;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.fillstyle.FillStyle;
import com.flagstone.transform.fillstyle.MorphSolidFill;
import com.flagstone.transform.linestyle.LineStyle;
import com.flagstone.transform.linestyle.MorphLineStyle;
import com.flagstone.transform.shape.Curve;
import com.flagstone.transform.shape.DefineMorphShape;
import com.flagstone.transform.shape.Line;
import com.flagstone.transform.shape.Shape;
import com.flagstone.transform.shape.ShapeRecord;
import com.flagstone.transform.shape.ShapeStyle;

/*
 * This example shows how to morph a shape.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.ChangeShape file-out
 *
 * where:
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class ChangeShape {
    public static void main(String[] args) {
        try {
            String out = args.length == 0 ? "ChangeShape.swf" : args[0];
            ChangeShape example = new ChangeShape();
            Movie movie = new Movie();
            example.createMovie(movie);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createMovie(Movie movie) {

        MovieHeader header = new MovieHeader();
        header.setFrameRate(12.0f);
        header.setFrameSize(new Bounds(0, 0, 8000, 8000));
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));

        Shape rectangle = createRectangle();
        Shape circle = createCircle();

        Bounds startBounds = new Bounds(-2000, -2000, 2000, 2000);
        Bounds endBounds = new Bounds(-2000, -2000, 2000, 2000);

        List<LineStyle> lineStyles = new ArrayList<LineStyle>();
        List<FillStyle> fillStyles = new ArrayList<FillStyle>();

        lineStyles.add(new MorphLineStyle(20, 20, WebPalette.BLACK.color(),
                WebPalette.WHITE.color()));
        fillStyles.add(new MorphSolidFill(WebPalette.BLACK.color(),
                WebPalette.WHITE.color()));

        DefineMorphShape shape = new DefineMorphShape(1, startBounds,
                endBounds, fillStyles, lineStyles, rectangle, circle);

        movie.add(shape);
        movie.add(Place2.show(shape.getIdentifier(), 1, 4000, 4000)
                        .setRatio(0));
        movie.add(ShowFrame.getInstance());

        CoordTransform location = CoordTransform.translate(4000, 4000);

        for (int i = 4095; i <= 65535; i += 4096) {
            movie.add(new Place2().setType(PlaceType.MODIFY).setLayer(1)
                    .setRatio(i).setTransform(location));
            movie.add(ShowFrame.getInstance());
        }

        // Add a delay so the circle is displayed for 2 seconds
        for (int i = 0; i < 24; i++) {
            movie.add(ShowFrame.getInstance());
        }
    }

    private Shape createRectangle() {
        int width = 4000;
        int height = 4000;

        List<LineStyle> lineStyles = new ArrayList<LineStyle>();
        List<FillStyle> fillStyles = new ArrayList<FillStyle>();

        lineStyles.add(new MorphLineStyle(20, 20, WebPalette.BLACK.color(),
                WebPalette.WHITE.color()));
        fillStyles.add(new MorphSolidFill(WebPalette.BLACK.color(),
                WebPalette.WHITE.color()));

        /*
         * Create the outline of the shape. Morphing requires that both shapes
         * have the same number of vertices.
         */
        List<ShapeRecord> shapeRecords = new ArrayList<ShapeRecord>();

        shapeRecords.add(new ShapeStyle().setLineStyle(1).setFillStyle(1)
                .setMove(width / 2, 0));
        shapeRecords.add(new Line(0, height / 2));
        shapeRecords.add(new Line(-width / 2, 0));
        shapeRecords.add(new Line(-width / 2, 0));
        shapeRecords.add(new Line(0, -height / 2));
        shapeRecords.add(new ShapeStyle().setMove(-width / 2, 0));
        shapeRecords.add(new Line(0, -height / 2));
        shapeRecords.add(new Line(width / 2, 0));
        shapeRecords.add(new Line(width / 2, 0));
        shapeRecords.add(new Line(0, height / 2));

        return new Shape(shapeRecords);
    }

    private Shape createCircle() {
        int x = 0;
        int y = 0;

        int rx = 2000;
        int ry = 2000;

        int startX = (int) (0.707 * rx) + x;
        int startY = (int) (0.707 * ry) + y;

        int ax = (int) (0.293 * rx);
        int ay = (int) (0.293 * ry);
        int cx = (int) (0.414 * rx);
        int cy = (int) (0.414 * ry);

        /*
         * Create the outline of the shape. Morphing requires that both shapes
         * have the same number of vertices.
         */
        List<ShapeRecord> shapeRecords = new ArrayList<ShapeRecord>();

        shapeRecords.add(new ShapeStyle().setLineStyle(1).setFillStyle(1)
                .setMove(startX, startY));
        shapeRecords.add(new Curve(-ax, ay, -cx, 0));
        shapeRecords.add(new Curve(-cx, 0, -ax, -ay));
        shapeRecords.add(new Curve(-ax, -ay, 0, -cy));
        shapeRecords.add(new Curve(0, -cy, ax, -ay));
        shapeRecords.add(new Curve(ax, -ay, cx, 0));
        shapeRecords.add(new Curve(cx, 0, ax, ay));
        shapeRecords.add(new Curve(ax, ay, 0, cy));
        shapeRecords.add(new Curve(0, cy, -ax, ay));

        return new Shape(shapeRecords);
    }
}