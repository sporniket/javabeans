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
import com.sun.javadoc.TypeVariable;
import com.sun.javadoc.WildcardType;

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
	
	public static String computeOutputType(Type toOutput, Map<String, String> translations, Set<String> shortables)
	{
		StringBuilder _buffer = new StringBuilder();
		outputTypename(_buffer, toOutput, translations, shortables);
		return _buffer.toString() ;
	}
	
	private static void outputTypename(StringBuilder into, Type toOutput, Map<String, String> translations, Set<String> shortables)
	{
		//either TypeVariable, WildcardType or ParametrizedType
		
		//Parametrized type
		ParameterizedType _pType = toOutput.asParameterizedType() ;
		if(null != _pType)
		{
			into.append(computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables)) ;
			Type[] _typeArguments = _pType.typeArguments();
			for(int _i = 0 ; _i < _typeArguments.length ; _i++)
			{
				into.append((0 == _i)?"<":",");
				outputTypename(into, _typeArguments[_i], translations, shortables);
			}
			into.append(">") ;
			return ; //done
		}
		
		//Wildcard Type
		WildcardType _wType = toOutput.asWildcardType();
		if (null != _wType)
		{
			into.append("?") ;
			Type[] _extendsBounds = _wType.extendsBounds();
			if (_extendsBounds.length > 0)
			{
				into.append(" extends ") ;
				outputTypename(into, _extendsBounds[0], translations, shortables);
			}
			Type[] _superBounds = _wType.superBounds();
			if (_superBounds.length > 0)
			{
				into.append(" super ") ;
				outputTypename(into, _superBounds[0], translations, shortables);
			}
			return ; //done
		}
		
		TypeVariable _tVar = toOutput.asTypeVariable();
		if (null != _tVar)
		{
			into.append(_tVar.typeName());
			Type[] _bounds = _tVar.bounds();
			if (_bounds.length > 0)
			{
				into.append(" extends ") ;
				outputTypename(into, _bounds[0], translations, shortables);
			}
			return ; //done
		}
		
		// else normal type.
		into.append(ClassUtils.computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
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
