/**
 *
 */
package com.sporniket.libre.javabeans.doclet.expander;

/**
 * Interface of a java source code generator.
 *
 * @author dsporn
 *
 */
public interface JavaSourceGenerator
{
	default void generate()
	{
		outputPackageStatement();
		outputImportStatements();

		outputClassBegin();
		outputClassBody();
		outputClassEnd();
	}

	void outputClassBegin();

	void outputClassBody();

	void outputClassEnd();

	void outputImportStatements();

	void outputPackageStatement();
}
