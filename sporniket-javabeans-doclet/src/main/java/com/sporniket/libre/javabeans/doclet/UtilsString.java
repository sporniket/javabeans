package com.sporniket.libre.javabeans.doclet;

import com.sporniket.strings.StringPredicates;
import com.sporniket.strings.pipeline.StringTransformation;

public final class UtilsString
{
	public static final class Transformations
	{
		public static final StringTransformation CAPITALIZER = s -> s.substring(0, 1).toUpperCase() + s.substring(1);

		public static final StringTransformation UNCAPITALIZER = s -> s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public static final class TransformationFactories
	{
		public static final StringTransformation buildPrefixRemover(String prefix)
		{
			if (StringPredicates.IS_EMPTY.test(prefix))
			{
				return s -> s;
			}
			return s -> null != s && s.length() >= prefix.length() && s.startsWith(prefix) ? s.substring(prefix.length()) : s;
		}
	}
}
