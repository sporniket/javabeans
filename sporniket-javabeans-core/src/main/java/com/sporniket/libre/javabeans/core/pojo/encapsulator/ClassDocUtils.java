package com.sporniket.libre.javabeans.core.pojo.encapsulator;

import static com.sporniket.libre.javabeans.core.pojo.encapsulator.ClassUtils.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.Type;

/**
 * Utility class for {@link ClassDoc}
 * 
 * @author dsporn
 *
 */
public final class ClassDocUtils
{
	//FIXME move to ClassUtils
	public static Map<String, String> getTranslationMapWhenPojosAreSuffixed(Set<String> registry, Set<String> sourcePackages,
			String pojoSuffix)
	{
		final Map<String, String> result = new HashMap<>(registry.size());
		final Predicate<String> _isPojo = c -> sourcePackages.contains(getPackageName(c))
				&& !pojoSuffix.equals(getSimpleName(c)) && c.endsWith(pojoSuffix);

		registry.stream().filter(_isPojo).forEach(c -> {
			result.put(c, ClassUtils.removeSuffixFromClassName(c, pojoSuffix));
		});

		return result;
	}


	/**
	 * Add into a collection of 'known classes' the specified class, its superclass and implemented interfaces, and the type of its
	 * public fields.
	 *
	 * @param knownClasses
	 *            the collection to update.
	 * @param toScan
	 *            the class to scan.
	 */
	public static void updateKnowClasses(Collection<String> knownClasses, ClassDoc toScan)
	{
		final Predicate<String> _isNotRegistered = i -> !knownClasses.contains(i);
		final Consumer<String> _registerKnownClass = i -> knownClasses.add(i); // do not support parametrized types
		final Consumer<Type> _processType = t -> updateKnownClasses(knownClasses, t);

		Arrays.asList(toScan.qualifiedName(), toScan.superclass().qualifiedName()).stream().filter(_isNotRegistered)
				.forEach(_registerKnownClass);

		Arrays.asList(toScan.interfaces()).stream().map(ClassDoc::qualifiedName).filter(_isNotRegistered)
				.forEach(_registerKnownClass);
		FieldDocUtils.getPublicFields(toScan).stream().map(FieldDoc::type).forEach(_processType);
	}


	/**
	 * Add into a collection of 'known classes' the specified type, and the type of its
	 * parameters if any.
	 *
	 * @param knownClasses
	 *            the collection to update.
	 * @param toScan
	 *            the type to scan.
	 */
	private static void updateKnownClasses(Collection<String> knownClasses, Type toScan)
	{
		ParameterizedType _pt = toScan.asParameterizedType();
		if (null != _pt)
		{
			Arrays.asList(_pt.typeArguments()).forEach(t -> updateKnownClasses(knownClasses, t));
		}
		String _qualifiedTypeName = toScan.qualifiedTypeName();
		if (!knownClasses.contains(_qualifiedTypeName))
		{
			knownClasses.add(_qualifiedTypeName);
		}
	}

}
