package com.sporniket.libre.javabeans.models.producers.javadoc.typescanner;

public class TypeParameter
{
	private String myDeclaredType;

	private String myInvokedType;

	public String getDeclaredType()
	{
		return myDeclaredType;
	}

	public void setDeclaredType(final String declaredType)
	{
		myDeclaredType = declaredType;
	}

	public String getInvokedType()
	{
		return myInvokedType;
	}

	public void setInvokedType(final String invokedType)
	{
		myInvokedType = invokedType;
	}

}
