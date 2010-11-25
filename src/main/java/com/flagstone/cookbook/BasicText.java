package com.flagstone.cookbook;
/*
 * BasicText.java
 * Cookbook ~
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
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.font.DefineFont2;
import com.flagstone.transform.text.DefineText2;
import com.flagstone.transform.util.font.AWTDecoder;
import com.flagstone.transform.util.font.Font;
import com.flagstone.transform.util.text.CharacterSet;
import com.flagstone.transform.util.text.TextTable;

/*
 * This example shows how to display static text fields in a movie.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicText string file-out
 *
 * where:
 *
 *     string, the text that will be displayed - enclose in quotes if the string
 *     contains spaces.
 *
 *     file-out is the path where the file will be written. If no output file
 *     is specified then a file named after the example will be written to the
 *     current directory.
 */
public class BasicText {
    public static void main(String[] args) {
        try {
            String str = args[0];
            String out = args.length == 1 ? "BasicText.swf" : args[1];
            BasicText example = new BasicText();
            Movie movie = new Movie();
            example.createMovie(movie, str);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createMovie(Movie movie, String str) throws IOException,
            DataFormatException {

        int uid = 1;
        int layer = 1;

        final String fontName = "Arial";
        final int fontSize = 24;
        final int fontStyle = java.awt.Font.PLAIN;

        // Load the AWT font.
        final AWTDecoder fontDecoder = new AWTDecoder();
        fontDecoder.read(new java.awt.Font(fontName, fontStyle, fontSize));
        final Font font = fontDecoder.getFonts().get(0);

        // Create a table of the characters displayed.
        final CharacterSet set = new CharacterSet();
        set.add(str);

        // Define the font containing only the characters displayed.
        DefineFont2 fontDef = font.defineFont(uid++, set.getCharacters());

        // Generate the text field used for the button text.
        final TextTable textGenerator = new TextTable(fontDef, fontSize * 20);
        DefineText2 text = textGenerator.defineText(uid++, str,
                WebPalette.BLACK.color());

        /*
         * Define the size of the Flash Player screen using the bounding
         * rectangle defined for the block of text plus a suitable margin so the
         * text does not touch the edge of the screen.
         */
        int padding = 1000;
        int screenWidth = text.getBounds().getWidth() + padding;
        int screenHeight = text.getBounds().getHeight() + padding;

        /*
         * Add all the objects together to create the movie. The baseline for
         * the text is halfway down the screen.
         */
        MovieHeader header = new MovieHeader();
        header.setFrameRate(1.0f);
        header.setFrameSize(new Bounds(0, 0, screenWidth, screenHeight));
        movie.add(header);

        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));
        movie.add(fontDef);
        movie.add(text);
        movie.add(Place2.show(text.getIdentifier(), layer++, padding / 2,
                screenHeight / 2));
        movie.add(ShowFrame.getInstance());
    }
}
