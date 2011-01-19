package com.flagstone.cookbook;
/*
 * BasicShapes.java
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

import com.flagstone.transform.Background;
import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieHeader;
import com.flagstone.transform.Place2;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.fillstyle.SolidFill;
import com.flagstone.transform.linestyle.LineStyle1;
import com.flagstone.transform.util.shape.Canvas;

/*
 * This example shows how the drawing commands available in the ShapeConstructor
 * can be used to draw simple geometric shapes and arbitrary paths. The example
 * draws a series of simple geometric shapes and discusses how the line and fill
 * styles are specified to render the paths drawn.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicShapes file-out
 *
 * where:
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicShapes {
    public static void main(String[] args) {
        try {
            String out = args.length == 0 ? "BasicShapes.swf" : args[0];
            BasicShapes example = new BasicShapes();
            Movie movie = new Movie();
            example.createMovie(movie);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMovie(Movie movie) {
        /*
         * Canvas allows coordinates to be specified either in twips or pixels.
         * Pixels makes life a little easier.
         */
        Canvas path = new Canvas();
        path.setPixels(true);

        int uid = 1;
        int width = 150;
        int height = 100;
        int cornerRadius = 10;
        int identifier = 0;

        MovieHeader header = new MovieHeader();
        header.setFrameRate(1.0f);
        header.setFrameSize(new Bounds(-4000, -4000, 4000, 4000));
        movie.add(header);
        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));

        /*
         * Draw a rectangle with the origin at the centre of the shape.
         */
        identifier = uid++;
        rect(path, width / 2, -height / 2, width, height);
        movie.add(path.defineShape(identifier));
        movie.add(Place2.show(identifier, 1, 0, 0));
        movie.add(ShowFrame.getInstance());

        /*
         * Draw a rectangle with rounded corners.
         */
        identifier = uid++;
        rect(path, width / 2, height / 2, width, height, cornerRadius);
        movie.add(path.defineShape(identifier));
        movie.add(Place2.replace(identifier, 1));
        movie.add(ShowFrame.getInstance());

        /*
         * Draw a circle.
         */
        identifier = uid++;
        circle(path, -width / 2, height / 2, height / 2);
        movie.add(path.defineShape(identifier));
        movie.add(Place2.replace(identifier, 1));
        movie.add(ShowFrame.getInstance());

        /*
         * Draw an ellipse.
         */
        identifier = uid++;
        ellipse(path, -width / 2, -height / 2, width / 2, height / 2);
        movie.add(path.defineShape(identifier));
        movie.add(Place2.replace(identifier, 1));
        movie.add(ShowFrame.getInstance());

        /*
         * Draw a polyline. The first point of a polyline is a move relative to
         * the current drawing point - which for a new path is (0,0). While the
         * geometric shapes drawn previous were all closed the polygon method
         * can be used to draw part of a shape so the newPath() and styles must
         * be explicitly specified to draw the shape.
         */
        identifier = uid++;

        int[] points = new int[] { 0, -100, 10, 0, 0, 90, 90, 0, 0, 20, -90, 0,
                0, 90, -20, 0, 0, -90, -90, 0, 0, -20, 90, 0, 0, -90, 10, 0 };

        path.clear();
        path.setLineStyle(new LineStyle1(20, WebPalette.BLACK.color()));
        path.setFillStyle(new SolidFill(WebPalette.RED.color()));
        path.rpolygon(points);
        path.close();

        movie.add(path.defineShape(identifier));
        movie.add(Place2.replace(identifier, 1));
        movie.add(ShowFrame.getInstance());

        /*
         * Draw a cubic bezier curve.
         *
         * This simple curve is included to show how cubic Bezier curves are
         * drawn. Flash only directly supports quadratic Bezier curves.
         * Converting from cubic to quadratic is mathematically difficult so
         * the cubic curve is flattened and drawn as a series of straight lines.
         * The results are visually appealing and the slight increase in the
         * size of the Flash file is compensated for by the increase in drawing
         * performance. Note that the path is closed before the shape is
         * generated. The Canvas tracks the initial and current drawing points.
         * When a call to close() is made it draws a straight line (if required)
         * between the current point and the initial point to ensure that the
         * shape is closed and will be rendered correctly.
         */
        identifier = uid++;

        path.clear();
        path.setLineStyle(new LineStyle1(20, WebPalette.BLACK.color()));
        path.setFillStyle(new SolidFill(WebPalette.RED.color()));
        path.curve(0, -100, 150, -100, 150, 0);
        path.close();

        movie.add(path.defineShape(identifier));
        movie.add(Place2.replace(identifier, 1));
        movie.add(ShowFrame.getInstance());
    }

    private void rect(Canvas path, int x, int y, int width, int height) {
        path.clear();
        path.setLineStyle(new LineStyle1(20, WebPalette.BLACK.color()));
        path.setFillStyle(new SolidFill(WebPalette.RED.color()));
        path.move(x - width / 2, y - height / 2);
        path.rline(width, 0);
        path.rline(0, height);
        path.rline(-width, 0);
        path.rline(0, -height);
        path.close();
    }

    private void rect(Canvas path, int x, int y, int width, int height,
            int radius) {
        int shortestSide = (height < width) ? height : width;

        if (radius > shortestSide / 2)
            radius = shortestSide / 2;

        path.clear();
        path.setLineStyle(new LineStyle1(20, WebPalette.BLACK.color()));
        path.setFillStyle(new SolidFill(WebPalette.RED.color()));
        path.move(x, y - height / 2);
        path.rline(width / 2 - radius, 0);
        path.rcurve(radius, 0, 0, radius);
        path.rline(0, height - 2 * radius);
        path.rcurve(0, radius, -radius, 0);
        path.rline(-(width - 2 * radius), 0);
        path.rcurve(-radius, 0, 0, -radius);
        path.rline(0, -(height - 2 * radius));
        path.rcurve(0, -radius, radius, 0);
        path.close();
    }

    private void ellipse(Canvas path, int x, int y, int rx, int ry) {
        boolean wasInPixels = path.isPixels();

        if (wasInPixels) {
            path.setPixels(false);

            x *= 20;
            y *= 20;
            rx *= 20;
            ry *= 20;
        }

        int startX = (int) (0.707 * rx) + x;
        int startY = (int) (0.707 * ry) + y;

        int ax = (int) (0.293 * rx);
        int ay = (int) (0.293 * ry);
        int cx = (int) (0.414 * rx);
        int cy = (int) (0.414 * ry);

        path.clear();
        path.setLineStyle(new LineStyle1(20, WebPalette.BLACK.color()));
        path.setFillStyle(new SolidFill(WebPalette.RED.color()));
        path.move(startX, startY);
        path.rcurve(-ax, ay, -cx, 0);
        path.rcurve(-cx, 0, -ax, -ay);
        path.rcurve(-ax, -ay, 0, -cy);
        path.rcurve(0, -cy, ax, -ay);
        path.rcurve(ax, -ay, cx, 0);
        path.rcurve(cx, 0, ax, ay);
        path.rcurve(ax, ay, 0, cy);
        path.rcurve(0, cy, -ax, ay);
        path.close();

        path.setPixels(wasInPixels);
    }

    private void circle(final Canvas path, int x, int y, int r) {
        ellipse(path, x, y, r, r);
    }
}