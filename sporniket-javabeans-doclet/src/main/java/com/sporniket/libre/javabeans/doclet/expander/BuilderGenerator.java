package com.sporniket.libre.javabeans.doclet.expander;

public interface BuilderGenerator
{
	default void generate()
	{
		outputClassBegin();
		outputFields();
		outputSetters();
		outputClassEnd();
	}

	void outputClassBegin();

	void outputClassEnd();

	void outputFields();

	void outputSetters();
}
