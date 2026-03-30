/**
 *
 */
package com.sporniket.libre.javabeans.models.consumers.javasource;

import static com.sporniket.libre.javabeans.models.consumers.javasource.Utils.NEXT_INDENTATION;

import java.io.PrintStream;
import java.util.List;

import com.sporniket.libre.javabeans.models.AnnotationParameterSpecs;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsValuesArray;
import com.sporniket.libre.javabeans.models.AnnotationSpecs;

/**
 * Annotation generator, independant of the type of java source code generator.
 * <p>
 * &copy; Copyright 2012-2023 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 23.07.00
 * @since 18.10.00
 */
public class AnnotationGenerator extends BasicGeneratorBase
{
	private String computeAnnotationValue(final AnnotationParameterSpecs annotation, final String indentation)
	{
		if (annotation instanceof AnnotationParameterSpecsSingleValue)
		{
			return computeAnnotationValue((AnnotationParameterSpecsSingleValue) annotation, indentation + NEXT_INDENTATION);
		}
		if (annotation instanceof AnnotationParameterSpecsValuesArray)
		{
			return computeAnnotationValue((AnnotationParameterSpecsValuesArray) annotation, indentation + NEXT_INDENTATION);
		}
		throw new IllegalStateException("Unsupported annotation parameter specs.");
	}

	private String computeAnnotationValue(final AnnotationParameterSpecsSingleValue annotation, final String indentation)
	{
		return outputAnnotationParameterValue(annotation);
	}

	private String computeAnnotationValue(final AnnotationParameterSpecsValuesArray annotation, final String indentation)
	{
		final StringBuilder _result = new StringBuilder();
		if (null != annotation.getPrefix())
		{
			final String _prefix = annotation.getPrefix().trim();
			_result.append(_prefix);
		}
		_result.append("{");
		final int _startLength = _result.length();
		for (final AnnotationParameterSpecsSingleValue _value : annotation.getValues())
		{
			if (_result.length() > _startLength)
			{
				_result.append(", ");
			}
			_result.append("\n      ").append(indentation).append(outputAnnotationParameterValue(_value));
		}
		_result.append("\n").append(indentation).append("}");
		if (null != annotation.getPostfix())
		{
			final String _postfix = annotation.getPostfix().trim();
			_result.append(_postfix);
		}
		return _result.toString();
	}

	public void outputAnnotation(final AnnotationSpecs annotations, final String indentation, final PrintStream out)
	{
		final List<AnnotationParameterSpecs> _parameters = annotations.getParameters();
		if (null != _parameters && !_parameters.isEmpty())
		{
			String _format = indentation + "@%s(%s)\n";
			final AnnotationParameterSpecs _firstParameter = _parameters.get(0);
			final StringBuilder _parametersValueStatement = new StringBuilder();
			if (1 == _parameters.size() && "value".equals(_firstParameter.getName()))
			{
				_parametersValueStatement.append(computeAnnotationValue(_firstParameter, indentation));
			}
			else
			{
				_format = indentation + "@%s(%s" + indentation + ")\n";
				final String _nextIndentation = indentation + NEXT_INDENTATION;
				// explicite parameters
				for (final AnnotationParameterSpecs _parameter : _parameters)
				{
					if (_parametersValueStatement.length() > 0)
					{
						_parametersValueStatement.append(", ");
					}
					_parametersValueStatement.append("\n").append(_nextIndentation)//
							.append(_parameter.getName()).append(" = ")
							.append(computeAnnotationValue(_parameter, _nextIndentation));
				}
				_parametersValueStatement.append("\n");
			}
			out.printf(_format, annotations.getType(), _parametersValueStatement.toString());
		}
		else
		{
			out.printf(indentation + "@%s\n", annotations.getType());
		}
	}

	private String outputAnnotationParameterValue(final AnnotationParameterSpecsSingleValue value)
	{
		return (value.isString()) ? String.format("\"%s\"", value.getValue()) : value.getValue().toString();
	}

}
