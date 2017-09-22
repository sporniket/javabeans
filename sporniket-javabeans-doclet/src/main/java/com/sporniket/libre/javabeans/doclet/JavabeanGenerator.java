/**
 *
 */
package com.sporniket.libre.javabeans.doclet;

/**
 * @author dsporn
 *
 */
public interface JavabeanGenerator extends JavaSourceGenerator
{
	void outputAccessors();

	@Override
	default void outputClassBody()
	{
		outputFields();

		outputAccessors();
	}

	void outputFields();

}
