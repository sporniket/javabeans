package com.sporniket.libre.javabeans.seeg;

/**
 * Internal representation of a column, will be converted into an object property.
 * 
 * @author dsporn
 *
 */
public class DefColumn extends Def
{
	public boolean generated;

	public String generationStrategy;

	public String javaType;

	public String temporalMapping;

	public boolean notNullable;
}
