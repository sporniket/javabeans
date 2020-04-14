package com.sporniket.libre.javabeans.seeg;

public interface CodeGenerator<DefinitionType>
{
	void generate(DefinitionType specs, String targetDir, String targetPackage);
}
