package com.sporniket.libre.javabeans.models.producers.javadoc.typescanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Intermediate model to store the list of type parameters found by {@link TypeParameterScanner}.
 */
public class TypeParameterCollection
{
	final List<TypeParameter> myParameters = new ArrayList<>();

	public Optional<String> getDeclaredTypeParameters() {
		return doGetTypeParameterString(TypeParameter::getDeclaredType);
	}

	public Optional<String> getInvokedTypeParameters() {
		return doGetTypeParameterString(TypeParameter::getInvokedType);
	}

	private Optional<String> doGetTypeParameterString(final Function<? super TypeParameter, ? extends String> mapper)
	{
		if (myParameters.isEmpty())
		{
			return Optional.empty();
		}
		return Optional.of(buildTypeList(mapper));
	}

	private String buildTypeList(final Function<? super TypeParameter, ? extends String> _mapper)
	{

		final StringBuilder result = new StringBuilder("<");
		myParameters.stream().map(_mapper).forEach(p -> {
			if (result.length() > 1)
			{
				result.append(", ");
			}
			result.append(p);
		});
		return result.toString();
	}

	public void append(final String declaredType, final String invokedType)
	{
		final TypeParameter tp = new TypeParameter() ;
		tp.setDeclaredType(declaredType);
		tp.setInvokedType(invokedType);
		myParameters.add(tp);
	}
}
