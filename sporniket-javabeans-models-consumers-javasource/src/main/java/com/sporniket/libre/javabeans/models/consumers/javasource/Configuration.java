package com.sporniket.libre.javabeans.models.consumers.javasource;

import java.util.HashSet;
import java.util.Set;

/**
 * Configuration values for the generators.
 *
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
 * @since 17.09.00
 */
public class Configuration
{
	private static final Set<String> KNOWN_ANNOTATIONS_FOR_GETTER = Set.of( //
			"java.lang.Deprecated", //
			"com.fasterxml.jackson.annotation.JsonProperty", //
			"com.fasterxml.jackson.annotation.JsonPropertyDescription" //
	);

	private static final Set<String> KNOWN_ANNOTATIONS_FOR_SETTER = Set.of( //
			"java.lang.Deprecated", //
			"com.fasterxml.jackson.annotation.JsonProperty", //
			"com.fasterxml.jackson.annotation.JsonPropertyDescription" //
	);

	/**
	 * Prefix used for the fields of the javabeans.
	 */
	String myBeanFieldPrefix = "my";

	/**
	 * Suffix of a generated Builder for a Javabean.
	 */
	String myBuilderSuffix = "_Builder";

	/**
	 * Suffix of a Pojo that should be expanded into a Javabean.
	 */
	String myPojoSuffix = "Raw";

	/**
	 * Internal, set of annotations from {@link #myAddAnnotationToGetter}.
	 *
	 * @since 20.04.00
	 */
	private Set<String> myAnnotationsToAddToGetters = null;

	/**
	 * Internal, set of annotations from {@link #myAddAnnotationToSetter}.
	 *
	 * @since 20.04.00
	 */
	private Set<String> myAnnotationsToAddToSetters = null;

	public String getBeanFieldPrefix()
	{
		return myBeanFieldPrefix;
	}

	public String getBuilderSuffix()
	{
		return myBuilderSuffix;
	}

	public String getPojoSuffix()
	{
		return myPojoSuffix;
	}

	/**
	 * @return the set of annotations (fully qualified names) that must be generated on the getter.
	 * @since 20.04.00
	 */
	public Set<String> getAnnotationsToAddToGetters()
	{
		if (null == myAnnotationsToAddToGetters)
		{
			myAnnotationsToAddToGetters = new HashSet<String>();
			myAnnotationsToAddToGetters.addAll(KNOWN_ANNOTATIONS_FOR_GETTER);

		}
		return myAnnotationsToAddToGetters;
	}

	/**
	 * @return the set of annotations (fully qualified names) that must be generated on the setter.
	 * @since 20.04.00
	 */
	public Set<String> getAnnotationsToAddToSetters()
	{
		if (null == myAnnotationsToAddToSetters)
		{
			myAnnotationsToAddToSetters = new HashSet<String>();
			myAnnotationsToAddToSetters.addAll(KNOWN_ANNOTATIONS_FOR_SETTER);

		}
		return myAnnotationsToAddToSetters;
	}

	public void setBeanFieldPrefix(final String beanFieldPrefix)
	{
		myBeanFieldPrefix = beanFieldPrefix;
	}

	public void setBuilderSuffix(final String builderSuffix)
	{
		myBuilderSuffix = builderSuffix;
	}

	public void setPojoSuffix(final String pojoSuffix)
	{
		myPojoSuffix = pojoSuffix;
	}

	public void setAnnotationsToAddToGetters(final Set<String> annotationsToAddToGetters)
	{
		myAnnotationsToAddToGetters = annotationsToAddToGetters;
	}

	public void setAnnotationsToAddToSetters(final Set<String> annotationsToAddToSetters)
	{
		myAnnotationsToAddToSetters = annotationsToAddToSetters;
	}

}