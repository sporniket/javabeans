/**
 *
 */
package com.sporniket.libre.javabeans.doclet.expander;

/**
 * @author dsporn
 *
 */
public interface JavabeanGenerator
{
	default void generate()
	{
		outputPackageStatement();
		outputImportStatements();
		outputClassBegin();
		outputFields();
		outputAccessors();
		outputClassEnd();
	}

	void outputAccessors();

	void outputClassBegin();

	void outputClassEnd();

	void outputFields();

	void outputImportStatements();

	void outputPackageStatement();
}
