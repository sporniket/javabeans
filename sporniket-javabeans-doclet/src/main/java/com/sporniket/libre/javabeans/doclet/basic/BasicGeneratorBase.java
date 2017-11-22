package com.sporniket.libre.javabeans.doclet.basic;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sun.javadoc.ClassDoc;

/**
 * A base to implements the generators of java source.
 * 
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; doclet</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211; 
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * @version 17.09.01
 * @since 17.09.00
 */
public class BasicGeneratorBase
{

	private ClassSpecs myClassSpecs;

	@Deprecated
	private Set<String> myKnownClasses;

	private DocletOptions myOptions ;
	
	private PrintStream myOut;

	@Deprecated
	private Set<String> myShortables;

	@Deprecated
	private ClassDoc mySource;


	@Deprecated
	private Map<String, String> myTranslations;


	public BasicGeneratorBase()
	{
		super();
	}
	

	public ClassSpecs getClassSpecs()
	{
		return myClassSpecs;
	}

	@Deprecated
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

	@Deprecated
	public Set<String> getShortables()
	{
		return myShortables;
	}


	@Deprecated
	public ClassDoc getSource()
	{
		return mySource;
	}


	@Deprecated
	public Map<String, String> getTranslations()
	{
		return myTranslations;
	}


	public void setClassSpecs(ClassSpecs classSpecs)
	{
		myClassSpecs = classSpecs;
	}

	@Deprecated
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


	@Deprecated
	public void setShortables(Set<String> shortables)
	{
		myShortables = shortables;
	}


	@Deprecated
	public void setSource(ClassDoc source)
	{
		mySource = source;
	}


	@Deprecated
	public void setTranslations(Map<String, String> translations)
	{
		myTranslations = translations;
	}

}