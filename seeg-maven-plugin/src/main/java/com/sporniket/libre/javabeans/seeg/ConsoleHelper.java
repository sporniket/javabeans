package com.sporniket.libre.javabeans.seeg;

import static java.lang.String.format;

import java.io.PrintStream;

public class ConsoleHelper
{

	private static final String FORMAT_HEADER = "===-===-===-===-<[ %s ]>-===-===-===-===";

	public static void printHeader(PrintStream out, String title)
	{
		out.println(format(FORMAT_HEADER, title));
	}

}
