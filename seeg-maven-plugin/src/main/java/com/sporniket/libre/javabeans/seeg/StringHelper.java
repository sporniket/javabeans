package com.sporniket.libre.javabeans.seeg;

public class StringHelper
{
	private static String camelCamelFromSnakeCase(String snaked, boolean firstLetterIsCapitalized)
	{
		String[] parts = snaked.split("_");
		StringBuilder b = new StringBuilder();
		for (String part : parts)
		{
			b.append(0 == b.length() && !firstLetterIsCapitalized ? part : capitalizeFirstLetter(part));
		}
		return b.toString();
	}

	public static String camelCaseCapitalizedFromSnakeCase(String snaked)
	{
		return camelCamelFromSnakeCase(snaked, true);
	}

	public static String camelCaseUncapitalizedFromSnakeCase(String snaked)
	{
		return camelCamelFromSnakeCase(snaked, false);
	}

	private static String capitalizeFirstLetter(String v)
	{
		return v.substring(0, 1).toUpperCase() + v.substring(1);
	}

	public static String uncapitalizeFirstLetter(String v)
	{
		return v.substring(0, 1).toLowerCase() + v.substring(1);
	}

	public static String undoublequote(String value)
	{
		if (value.startsWith("\""))
		{
			value = value.substring(1);
		}
		if (value.endsWith("\""))
		{
			value = value.substring(0, value.length() - 1);
		}
		return value;
	}

}
