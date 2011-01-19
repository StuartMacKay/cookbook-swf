---------------------
  Cookbook For Java
---------------------

This Cookbook is a collection of classes that illustrate how to use the classes 
in the Transform and Translate frameworks.

----------------
  Requirements
----------------

To compile and run the examples you must have:

	Java 2 Standard Edition
	
JAR files for the current versions of Transform SWF and Translate SWF are 
included.
	
-------------------------
  Building the Examples
-------------------------

A JAR file is available containing all the classes along with those from 
Transform 3.0 and Translate 3.0 libraries. However you can also re-build all 
the JAR files using Maven.

------------------------
  Running the Examples
------------------------

Each of the examples implements the main() method so the example can be run by 
specifying the name of the class on the command line, for example:

	java -cp <classpath> com.flagstone.cookbook.DisplayList [file-out]

where:

   classpath =  cookbook-1.0.jar;transform-3.0.jar;translate-3.0.jar	


Each of the examples generates a Flash file which is named after the class, e.g,
'DisplayList.swf', which is written to the current directory. You can also pass
a path as an argument where the file will be written.

The file can be viewed using any web browser that supports the Flash plug-in or
using any stand-alone Flash Player.
	
Several of the examples use arguments passed on the command line. For example 
the BasicImage example displays an image from a file. The documentation for 
each class lists any arguments supported.

--------------------------
  Additional Information
--------------------------

For Further Information please contact:

Stuart MacKay
Flagstone Software Ltd.
92 High Street
Wick, Caithness KW1 4LY
Scotland

www.flagstonesoftware.com
