package com.sporniket.libre.javabeans.processors.generators.javasource;

import static com.sporniket.libre.javabeans.processors.generators.javasource.Utils.IS_NOT_JAVA_LANG_TYPE;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UtilsTest
{
	@Nested
	class Describe_isNotJavaLangType
	{
		@Test
		void should_return_true_when_fully_qualified_type_is_in_java_lang_package()
		{
			then(IS_NOT_JAVA_LANG_TYPE.test("java.lang.Whatever")).isFalse();
			then(IS_NOT_JAVA_LANG_TYPE.test("java.lang.whatever.Whatever")).isTrue();
		}
	}
}
