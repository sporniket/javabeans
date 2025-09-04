package com.sporniket.libre.javabeans.processors.generators.javasource;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jdk.javadoc.doclet.Doclet.Option;
import jdk.javadoc.doclet.Reporter;

/**
 * Command line arguments supported by the javabeans doclets.
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
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with <i>The Sporniket Javabeans Library &#8211; core</i>. If not, see
 * <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 23.07.00
 * @since 17.09.00
 */
public class DocletOptions {
	private static final String FORMAT_TO_STRING = "beanFieldPrefix={0}\nbuilderSuffix={1}\nd={2}\npojoSuffix={3}\naddAnnotationToGetter={4}\naddAnnotationToSetter={5}";

	private static final String KNOWN_ANNOTATIONS_FOR_GETTER = "java.lang.Deprecated,com.fasterxml.jackson.annotation.JsonProperty,com.fasterxml.jackson.annotation.JsonPropertyDescription";

	private static final String KNOWN_ANNOTATIONS_FOR_SETTER = "java.lang.Deprecated,com.fasterxml.jackson.annotation.JsonProperty,com.fasterxml.jackson.annotation.JsonPropertyDescription";

	private final Reporter myReporter;

	protected DocletOptions(Reporter reporter) {
		super();
		myReporter = reporter;
	}

	/**
	 * List of annotation (fully qualified names separated with comma) that have to
	 * be put on the getter in addition to the field.
	 * 
	 * @since 20.04.00
	 */
	String myAddAnnotationToGetter;

	/**
	 * List of annotation (fully qualified name separated with comma) that have to
	 * be put on the setter in addition to the field.
	 * 
	 * @since 20.04.00
	 */
	String myAddAnnotationToSetter;

	/**
	 * Prefix used for the fields of the javabeans.
	 */
	String myBeanFieldPrefix = "my";

	/**
	 * Suffix of a generated Builder for a Javabean.
	 */
	String myBuilderSuffix = "_Builder";

	/**
	 * Store the <code>-d</code> option value (target directory).
	 */
	String myD;

	/**
	 * Suffix of a Pojo that should be expanded into a Javabean.
	 */
	String myPojoSuffix = "Raw";

	public List<Option> getJavaDocletOptions() {
		return List.of(new DocletOptionWithStringArgument(//
				"--addAnnotationToGetter", //
				"List of annotation (fully qualified names separated with comma) that have to be put on the getter in addition to the field.", //
				this, getReporter()),
				new DocletOptionWithStringArgument(//
						"--addAnnotationToSetter", //
						"List of annotation (fully qualified name separated with comma) that have to be put on the setter in addition to the field.", //
						this, getReporter()),
				new DocletOptionWithStringArgument(//
						"--beanFieldPrefix", //
						"Prefix used for the fields of the javabeans.", //
						this, getReporter()),
				new DocletOptionWithStringArgument(//
						"--builderSuffix", //
						"Suffix of a generated Builder for a Javabean.", //
						this, getReporter()),
				new DocletOptionWithStringArgument(//
						"--pojoSuffix", //
						"Suffix of a Pojo that should be expanded into a Javabean.", //
						this, getReporter()),
				new DocletOptionWithStringArgument(//
						"-d", //
						"Store the <code>-d</code> option value (target directory).", //
						this, getReporter())

		);
	}

	private Reporter getReporter() {
		return myReporter;
	}

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

	public String getAddAnnotationToGetter() {
		return (null != myAddAnnotationToGetter) ? KNOWN_ANNOTATIONS_FOR_GETTER + "," + myAddAnnotationToGetter
				: KNOWN_ANNOTATIONS_FOR_GETTER;
	}

	public String getAddAnnotationToSetter() {
		return (null != myAddAnnotationToSetter) ? KNOWN_ANNOTATIONS_FOR_SETTER + "," + myAddAnnotationToSetter
				: KNOWN_ANNOTATIONS_FOR_SETTER;
	}

	public String getBeanFieldPrefix() {
		return myBeanFieldPrefix;
	}

	public String getBuilderSuffix() {
		return myBuilderSuffix;
	}

	public String getD() {
		return myD;
	}

	public String getPojoSuffix() {
		return myPojoSuffix;
	}

	@Override
	public String toString() {
		return MessageFormat.format(FORMAT_TO_STRING, getBeanFieldPrefix(), getBuilderSuffix(), getD(), getPojoSuffix(),
				getAddAnnotationToGetter(), getAddAnnotationToSetter());
	}

	/**
	 * @return the set of annotations (fully qualified names) that must be generated
	 *         on the getter.
	 * @since 20.04.00
	 */
	public Set<String> getAnnotationsToAddToGetters() {
		if (null == myAnnotationsToAddToGetters) {
			myAnnotationsToAddToGetters = new HashSet<String>();
			myAnnotationsToAddToGetters.addAll(//
					asList(getAddAnnotationToGetter().split(","))//
							.stream()//
							.filter(a -> a != null)//
							.map(a -> a.trim())//
							.filter(a -> a.length() > 0)//
							.collect(toList()));

		}
		return myAnnotationsToAddToGetters;
	}

	/**
	 * @return the set of annotations (fully qualified names) that must be generated
	 *         on the setter.
	 * @since 20.04.00
	 */
	public Set<String> getAnnotationsToAddToSetters() {
		if (null == myAnnotationsToAddToSetters) {
			myAnnotationsToAddToSetters = new HashSet<String>();
			myAnnotationsToAddToSetters.addAll(//
					asList(getAddAnnotationToSetter().split(","))//
							.stream()//
							.filter(a -> a != null)//
							.map(a -> a.trim())//
							.filter(a -> a.length() > 0)//
							.collect(toList()));

		}
		return myAnnotationsToAddToSetters;
	}

}