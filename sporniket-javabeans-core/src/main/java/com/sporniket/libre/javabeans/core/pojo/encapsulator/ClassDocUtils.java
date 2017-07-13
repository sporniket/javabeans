package com.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	public static Map<String, String> getTranslationMapWhenPojosAreSuffixed(Set<ClassDoc> registry, Set<String> sourcePackages,
			String pojoSuffix)
	{
		final Map<String, String> result = new HashMap<>(registry.size());
		final Predicate<ClassDoc> _isPojo = c -> sourcePackages.contains(c.containingPackage().name())
				&& !pojoSuffix.equals(c.simpleTypeName()) && c.simpleTypeName().endsWith(pojoSuffix);

		registry.stream().filter(_isPojo).forEach(c -> {
			result.put(c.qualifiedName(), ClassUtils.removeSuffixFromClassName(c.qualifiedName(), pojoSuffix));
		});

		return result;
	}

	public static void updateKnowClasses(List<String> knownClasses, ClassDoc toScan)
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

	private static void updateKnownClasses(List<String> knownClasses, Type toScan)
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
