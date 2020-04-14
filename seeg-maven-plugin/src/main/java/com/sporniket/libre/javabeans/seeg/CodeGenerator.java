package com.sporniket.libre.javabeans.seeg;

import java.io.PrintStream;

public interface CodeGenerator<DefinitionType>
{
	void generate(DefinitionType specs, String targetDir, String targetPackage, PrintStream out);
}
