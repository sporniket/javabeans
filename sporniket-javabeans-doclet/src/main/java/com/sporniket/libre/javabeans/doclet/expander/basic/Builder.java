/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.expander.basic;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.sun.javadoc.ClassDoc;

/**
 * Genenator builder.
 * 
 * @author dsporn
 *
 */
public class Builder<G extends BasicGenerator>
{
	private final G myGenerator;

	public Builder(G generator)
	{
		super();
		myGenerator = generator;
	}

	public G done()
	{
		return myGenerator;
	}

	public Builder<G> withKnownClasses(Set<String> knownClasses)
	{
		myGenerator.setKnownClasses(knownClasses);
		return this;
	}

	public Builder<G> withOut(PrintStream out)
	{
		myGenerator.setOut(out);
		return this;
	}

	public Builder<G> withShortables(Set<String> shortables)
	{
		myGenerator.setShortables(shortables);
		return this;
	}

	public Builder<G> withSource(ClassDoc source)
	{
		myGenerator.setSource(source);
		return this;
	}

	public Builder<G> withTranslations(Map<String, String> translations)
	{
		myGenerator.setTranslations(translations);
		return this;
	}
}
