package com.sporniket.libre.javabeans.seeg;

import java.io.PrintStream;

/**
 * Generator of source code.
 * 
 * @author dsporn
 *
 * @param <DefinitionType>
 *            the type of internal representation to convert into source code.
 */
public interface CodeGenerator<DefinitionType>
{
	void generate(DefinitionType specs, String targetDir, String targetPackage, PrintStream out);
}
