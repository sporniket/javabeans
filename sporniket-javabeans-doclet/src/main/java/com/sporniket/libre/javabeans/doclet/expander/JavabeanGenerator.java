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
	void outputPackageStatement();

	void outputImportStatements();

	void outputClassBegin();

	void outputBuilderPattern();

	void outputFields();

	void outputAccessors();

	void outputClassEnd();

	default void generate()
	{
		outputPackageStatement();
		outputImportStatements();
		outputClassBegin();
		outputBuilderPattern();
		outputFields();
		outputAccessors();
		outputClassEnd();
	}
}
