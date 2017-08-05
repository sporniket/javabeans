/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.encapsulator.basic;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.sun.javadoc.ClassDoc;

/**
 * Base class for generating java code.
 * @author dsporn
 *
 */
public class BasicGenerator
{
	private Set<String> myKnownClasses ;
	private PrintStream myOut ;
	private Set<String> myShortables ;
	private ClassDoc mySource ;
	private Map<String, String> myTranslations ;
	
	public Set<String> getKnownClasses()
	{
		return myKnownClasses;
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
