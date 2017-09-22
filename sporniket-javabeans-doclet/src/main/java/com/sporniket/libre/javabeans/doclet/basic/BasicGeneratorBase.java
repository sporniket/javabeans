package com.sporniket.libre.javabeans.doclet.basic;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sun.javadoc.ClassDoc;

/**
 * A base to implements the generators of java source.
 * 
 * @author dsporn
 *
 */
public class BasicGeneratorBase
{

	private Set<String> myKnownClasses;

	private DocletOptions myOptions ;

	private PrintStream myOut;

	private Set<String> myShortables;

	private ClassDoc mySource;
	
	private Map<String, String> myTranslations;

	public BasicGeneratorBase()
	{
		super();
	}

	public Set<String> getKnownClasses()
	{
		return myKnownClasses;
	}

	public DocletOptions getOptions()
	{
		return myOptions;
	}

	public PrintStream getOut()
	{
		return myOut;
	}

	public Set<String> getShortables()
	{
		return myShortables;
	}

	public ClassDoc getSource()
	{
		return mySource;
	}

	public Map<String, String> getTranslations()
	{
		return myTranslations;
	}

	public void setKnownClasses(Set<String> knownClasses)
	{
		myKnownClasses = knownClasses;
	}

	public void setOptions(DocletOptions options)
	{
		myOptions = options;
	}

	public void setOut(PrintStream out)
	{
		myOut = out;
	}

	public void setShortables(Set<String> shortables)
	{
		myShortables = shortables;
	}

	public void setSource(ClassDoc source)
	{
		mySource = source;
	}

	public void setTranslations(Map<String, String> translations)
	{
		myTranslations = translations;
	}

}