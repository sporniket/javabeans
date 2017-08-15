/**
 *
 */
package com.sporniket.libre.javabeans.doclet.expander;

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
