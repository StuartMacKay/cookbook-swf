package com.flagstone.cookbook;
/*
 * ShowFont.java
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
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.font.DefineFont2;
import com.flagstone.transform.text.DefineText2;
import com.flagstone.transform.util.font.AWTDecoder;
import com.flagstone.transform.util.font.Font;
import com.flagstone.transform.util.text.CharacterSet;
import com.flagstone.transform.util.text.TextTable;

/*
 * This example shows how to display a block of text in a movie.
 *
 * To run this example, type the following on a command line:
 *
 *     java -cp ... com.flagstone.cookbook.BasicTextBlock font-name file-out
 *
 * where:
 *
 *      font-name is the name of a Font available in Java such as Arial
 *
 *      file-out is the path where the file will be written. If no output file
 *      is specified then a file named after the example will be written to the
 *      current directory.
 */
public class BasicTextBlock {
    public static void main(String[] args) {
        try {
            String out = args.length == 1 ? "BasicTextBlock.swf" : args[1];
            BasicTextBlock example = new BasicTextBlock();
            Movie movie = new Movie();
            example.createMovie(movie, args[0]);
            movie.encodeToFile(new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createMovie(Movie movie, String fontName)
            throws DataFormatException, IOException {

        int uid = 1;
        int layer = 1;

        final int fontSize = 24;
        final int fontStyle = java.awt.Font.PLAIN;

        // Load the AWT font.
        final AWTDecoder fontDecoder = new AWTDecoder();
        fontDecoder.read(new java.awt.Font(fontName, fontStyle, fontSize));
        final Font font = fontDecoder.getFonts().get(0);

        final CharacterSet set = new CharacterSet();

        /*
         * Create the strings that will be used to display the text. The first
         * 256 characters available in the font will be shown as a block of 4
         * lines each containing 64 characters. Each character is added to the
         * CharacterSet so the font can be generated with the set of characters
         * displayed.
         */
        List<String> lines = new ArrayList<String>();
        char c = 0;

        for (int i = 0; i < 4; i++) {
            StringBuffer line = new StringBuffer();

            for (int j = 0; j < 64; j++, c++) {
                line.append(c);
            }
            lines.add(line.toString());
            set.add(line.toString());
        }

        DefineFont2 fontDef = font.defineFont(uid++, set.getCharacters());

        final TextTable textGenerator = new TextTable(fontDef, fontSize * 20);
        DefineText2 text = textGenerator.defineTextBlock(uid++, lines,
                WebPalette.BLACK.color(), fontSize * 20);

        /*
         * Define the size of the Flash Player screen using the bounding
         * rectangle defined for the block of text plus a suitable margin so the
         * text does not touch the edge of the screen.
         */
        int padding = 1000;
        int screenWidth = text.getBounds().getWidth() + padding;
        int screenHeight = text.getBounds().getHeight() + padding;

        /*
         * Add all the objects together to create the movie.
         */
        MovieHeader header = new MovieHeader();
        header.setFrameRate(1.0f);
        header.setFrameSize(new Bounds(0, 0, screenWidth, screenHeight));
        movie.add(header);
        movie.add(new Background(WebPalette.LIGHT_BLUE.color()));
        movie.add(fontDef);
        movie.add(text);
        movie.add(Place2.show(text.getIdentifier(), layer++, padding / 2,
                padding / 2));
        movie.add(ShowFrame.getInstance());
    }
}
