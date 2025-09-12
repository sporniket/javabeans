package com.sporniket.libre.javabeans.models.consumers.javasource;

import java.util.List;

import com.sporniket.libre.javabeans.models.FieldSpecs_Builder;

final class FieldSpecsFixtures
{
	static FieldSpecs_Builder setupBooleanField(final String typeInvocation, final String fieldPrefix, final String fieldName,
			final String accessorName)
	{
		return setupMinimalField(typeInvocation, fieldPrefix, fieldName, accessorName) //
				.withBooleanGetter(true) //
		;
	}

	static FieldSpecs_Builder setupArrayField(final String typeInvocation, final String fieldPrefix, final String fieldName,
			final String accessorName)
	{
		return setupMinimalField(typeInvocation, fieldPrefix, fieldName, accessorName) //
				.withArrayMarker("[]") //
		;
	}

	static FieldSpecs_Builder setupMinimalField(final String typeInvocation, final String fieldPrefix, final String fieldName,
			final String accessorName)
	{
		return new FieldSpecs_Builder()//
				.withDirectlyRequired(true)//
				.withFieldPrefix(fieldPrefix)//
				.withArrayMarker("")//
				.withNameForAccessor(accessorName)//
				.withNameForField(fieldName)//
				.withTypeInvocation(typeInvocation)//
				.withAnnotations(List.of())//
		;
	}

}
