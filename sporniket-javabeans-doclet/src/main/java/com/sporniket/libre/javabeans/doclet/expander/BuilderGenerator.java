package com.sporniket.libre.javabeans.doclet.expander;

public interface BuilderGenerator extends JavaSourceGenerator
{

	@Override
	default void outputClassBody()
	{
		outputFields();

		outputSetters();
	}

	void outputFields();

	void outputSetters();
}
