package com.flagstone.cookbook;
/*
 * BasicButton.java
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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import com.flagstone.transform.Background;
import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieHeader;
import com.flagstone.transform.Place2;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.action.Action;
import com.flagstone.transform.action.GetUrl;
import com.flagstone.transform.button.ButtonShape;
import com.flagstone.transform.button.ButtonState;
import com.flagstone.transform.button.DefineButton;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.Color;
import com.flagstone.transform.datatype.CoordTransform;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.fillstyle.SolidFill;
import com.flagstone.transform.font.DefineFont2;
import com.flagstone.transform.linestyle.LineStyle1;
import com.flagstone.transform.shape.DefineShape2;
import com.flagstone.transform.text.DefineText2;
import com.flagstone.transform.util.font.AWTDecoder;
import com.flagstone.transform.util.font.Font;
import com.flagstone.transform.util.shape.Canvas;
import com.flagstone.transform.util.text.CharacterSet;
import com.flagstone.transform.util.text.TextTable;

/*
 * This example shows how to create a button using the DefineButton class which
 * will display a web page in a browser when clicked.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicButton string file-out
 *
 * where:
 *
 *     string, the label that will be displayed on the button - enclose in
 *     quotes if the label contains spaces.
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicButton {
    public static void main(String[] args) {
        try {
            String label = args[0];
            String out = args.length == 1 ? "BasicButton.swf" : args[1];
            BasicButton example = new BasicButton();
            Movie movie = new Movie();
            example.createMovie(movie, label);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createMovie(Movie movie, String title)
            throws IOException, DataFormatException {

        int uid = 1;
        int layer = 1;

        final String fontName = "Arial";
        final int fontStyle = java.awt.Font.PLAIN;
        final int fontSize = 24;

        // Load the AWT font.
        final AWTDecoder fontDecoder = new AWTDecoder();
        fontDecoder.read(new java.awt.Font(fontName, fontStyle, fontSize));
        final Font font = fontDecoder.getFonts().get(0);

        // Create a table of the characters displayed.
        final CharacterSet set = new CharacterSet();
        set.add(title);

        // Define the font containing only the characters displayed.
        DefineFont2 fontDef = font.defineFont(uid++, set.getCharacters());

        // Generate the text field used for the button text.
        final TextTable textGenerator = new TextTable(fontDef, fontSize * 20);
        DefineText2 label = textGenerator.defineText(uid++, title,
                WebPalette.BLACK.color());

        /*
         * Define the padding amount separating the text from the edge of the
         * button and the edge of the button to the edge of the screen.
         */
        int buttonPadding = 200;
        int screenPadding = 2000;

        int buttonWidth = label.getBounds().getWidth() + buttonPadding;
        int buttonHeight = label.getBounds().getHeight() + buttonPadding;

        /*
         * Define colours used to fill the buttons in each of its states.
         */
        Color lineColor = WebPalette.BLACK.color();
        Color shadowColor = WebPalette.GRAY.color();
        Color upColor = WebPalette.RED.color();
        Color overColor = WebPalette.ORANGE.color();

        /*
         * For each of the layers that make up the button draw a basic rectangle
         * with rounded corners.
         */
        int lineWidth = 20;
        int cornerRadius = 100;

        Canvas path = new Canvas();

        path.clear();
        path.setLineStyle(new LineStyle1(lineWidth, shadowColor));
        path.setFillStyle(new SolidFill(shadowColor));
        rect(path, 0, 0, buttonWidth, buttonHeight, cornerRadius);
        path.close();

        DefineShape2 shadow = path.defineShape(uid++);

        path.clear();
        path.setLineStyle(new LineStyle1(lineWidth, lineColor));
        path.setFillStyle(new SolidFill(upColor));
        rect(path, 0, 0, buttonWidth, buttonHeight, cornerRadius);
        path.close();

        DefineShape2 upShape = path.defineShape(uid++);

        path.clear();
        path.setLineStyle(new LineStyle1(lineWidth, lineColor));
        path.setFillStyle(new SolidFill(overColor));
        rect(path, 0, 0, buttonWidth, buttonHeight, cornerRadius);
        path.close();

        DefineShape2 overShape = path.defineShape(uid++);

        /*
         * Calculate the position of the text so it is centred in the button.
         */
        int xOffset = -label.getBounds().getWidth() / 2;
        int yOffset = (buttonHeight - label.getBounds().getHeight()) / 2;

        /*
         * The button will cast as shadow when it in the up state. Recessing the
         * button by changing the location of the shapes when the button is
         * clicked allows a very simple animation to be performed. More complex
         * animations can be created by using movie clips.
         */
        int xShadow = 60;
        int yShadow = 60;

        /*
         * Define coordinate transform applied to the basic button shape to
         * perform a simple animation giving the impression the button was
         * physically clicked.
         */
        CoordTransform recess = CoordTransform.translate(xShadow, yShadow);

        /*
         * Add the button records that define the button's appearance
         */
        List<ButtonShape> records = new ArrayList<ButtonShape>();

        records.add(new ButtonShape().addState(ButtonState.ACTIVE)
                .setIdentifier(upShape.getIdentifier()).setLayer(layer++));

        records.add(new ButtonShape().addState(ButtonState.UP)
                .setIdentifier(shadow.getIdentifier()).setLayer(layer++)
                .setTransform(recess));

        records.add(new ButtonShape().addState(ButtonState.UP)
                .setIdentifier(upShape.getIdentifier()).setLayer(layer++));

        records.add(new ButtonShape().addState(ButtonState.UP)
                .setIdentifier(label.getIdentifier()).setLayer(layer++)
                .setTransform(CoordTransform.translate(xOffset, yOffset)));

        records.add(new ButtonShape().addState(ButtonState.OVER)
                .setIdentifier(shadow.getIdentifier()).setLayer(layer++)
                .setTransform(recess));

        records.add(new ButtonShape().addState(ButtonState.OVER)
                .setIdentifier(overShape.getIdentifier()).setLayer(layer++));

        records.add(new ButtonShape().addState(ButtonState.OVER)
                .setIdentifier(label.getIdentifier()).setLayer(layer++)
                .setTransform(CoordTransform.translate(xOffset, yOffset)));

        records.add(new ButtonShape().addState(ButtonState.DOWN)
                .setIdentifier(overShape.getIdentifier()).setLayer(layer++)
                .setTransform(recess));

        records.add(new ButtonShape().addState(ButtonState.DOWN)
                .setIdentifier(label.getIdentifier()).setLayer(layer++)
                .setTransform(CoordTransform.translate(
                        xOffset + xShadow, yOffset + yShadow)));

        /*
         * DefineButton executes a single set of actions when it is clicked.
         * More complex behaviour can be implemented using DefineButton2 which
         * allows different sets of actions to executed in response to a wide
         * range of events.
         */
        List<Action> actions = new ArrayList<Action>();
        actions.add(new GetUrl("http://www.flagstonesoftware.com", ""));

        DefineButton button = new DefineButton(uid++, records, actions);

        /***************************************************
         * Put all the objects together in a movie
         ***************************************************/

        int screenWidth = buttonWidth + screenPadding;
        int screenHeight = buttonHeight + screenPadding;

        MovieHeader header = new MovieHeader();
        header.setFrameSize(new Bounds(0, 0, screenWidth, screenHeight));
        header.setFrameRate(1.0f);
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));

        movie.add(fontDef);
        movie.add(shadow);
        movie.add(upShape);
        movie.add(overShape);
        movie.add(label);
        movie.add(button);

        movie.add(Place2.show(button.getIdentifier(), layer++, screenWidth / 2,
                screenHeight / 2));

        movie.add(ShowFrame.getInstance());
    }

    private void rect(Canvas path, int x, int y, int width, int height,
            int radius) {
        int shortestSide = (height < width) ? height : width;

        if (radius > shortestSide / 2)
            radius = shortestSide / 2;

        path.move(x, y - height / 2);
        path.rline(width / 2 - radius, 0);
        path.rcurve(radius, 0, 0, radius);
        path.rline(0, height - 2 * radius);
        path.rcurve(0, radius, -radius, 0);
        path.rline(-(width - 2 * radius), 0);
        path.rcurve(-radius, 0, 0, -radius);
        path.rline(0, -(height - 2 * radius));
        path.rcurve(0, -radius, radius, 0);
    }
}
