package com.sporniket.libre.javabeans.models.producers.javadoc;

import javax.lang.model.element.Name;

class NameString implements Name
{
	final private String myValue;

	public NameString(final String value)
	{
		myValue = value;
	}

	@Override
	public int length()
	{
		return myValue.length();
	}

	@Override
	public char charAt(final int index)
	{
		return myValue.charAt(index);
	}

	@Override
	public CharSequence subSequence(final int start, final int end)
	{
		return myValue.subSequence(start, end);
	}

	@Override
	public boolean contentEquals(final CharSequence cs)
	{
		return myValue.contentEquals(cs);
	}

}