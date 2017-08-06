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

	void outputBuilderSection();

	void outputFields();

	void outputAccessors();

	void outputClassEnd();

	default void generate()
	{
		outputPackageStatement();
		outputImportStatements();
		outputClassBegin();
		outputBuilderPattern();
		outputBuilderSection();
		outputFields();
		outputAccessors();
		outputClassEnd();
	}
}
