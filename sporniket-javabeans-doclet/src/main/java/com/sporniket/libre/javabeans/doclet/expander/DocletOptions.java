package com.sporniket.libre.javabeans.doclet.expander;

/**
 * Command line arguments supported by the javabeans doclets.
 * 
 * @author dsporn
 *
 */
public class DocletOptions
{
	/**
	 * Suffix of a generated Builder for a Javabean.
	 */
	String builderSuffix = "_Builder";

	/**
	 * Store the <code>-d</code> option value (target directory).
	 */
	String d;

	/**
	 * Suffix of a Pojo that should be expanded into a Javabean.
	 */
	String pojoSuffix = "Raw";

	public String getBuilderSuffix()
	{
		return builderSuffix;
	}

	public String getD()
	{
		return d;
	}
	
	public String getPojoSuffix()
	{
		return pojoSuffix;
	}
	
	
}