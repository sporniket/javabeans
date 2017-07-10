/**
 * 
 */
package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Encapsulate a given Pojo into a Bean.
 * 
 * @author dsporn
 *
 */
public class Encapsulator
{
	static Predicate<Field> IS_PUBLIC_FIELD = f -> 0 != (f.getModifiers() & Modifier.PUBLIC);

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException
	{
		Class<?> _toScan = Class.forName("test.sporniket.libre.javabeans.core.pojo.testsuite.SampleBasicBeanRaw");
		System.out.println(_toScan.getSimpleName());
		Arrays.asList(_toScan.getAnnotations()).forEach(a -> System.out.println("  annotation " + a.toString()));

		List<Field> _fields = Arrays.asList(_toScan.getDeclaredFields());
		for (Field _field : _fields.stream().filter(IS_PUBLIC_FIELD).collect(Collectors.toList()))
		{
			System.out.println("property : " + _field.getName() + " : " + _field.getType().getName());
			Arrays.asList(_field.getAnnotations()).forEach(a -> System.out.println("  " + "annotation " + a.toString()));
		}

		System.out.println("--- --- ");

		new Encapsulator().processPojoClass(_toScan);
	}

	private void processPojoClass(Class<?> toScan)
	{
		// Package
		System.out.printf("package %s;\n", toScan.getPackage().getName());

		System.out.println();

		// register superclass and interfaces
		List<Field> _beanFields = Arrays.asList(toScan.getDeclaredFields()).stream().filter(IS_PUBLIC_FIELD)
				.collect(Collectors.toList());

		registerKnownClasses(toScan, _beanFields);

		Predicate<? super Class<?>> _isJavaLangType = c -> !Object.class.getPackage().equals(c.getPackage());

		getKnownClasses().stream().filter(_isJavaLangType).map(c -> c.getName()).collect(Collectors.toCollection(TreeSet::new))
				.forEach(c -> System.out.printf("import %s;\n", c));

		System.out.println();

		Map<Class<?>, Class<?>> _translation = getTranslationMapWhenPojosAreSuffixed(getKnownClasses(),
				Arrays.asList(toScan.getPackage().getName()).stream().collect(Collectors.toCollection(TreeSet::new)), "Raw");
		if(_translation.isEmpty())
		{
			System.out.println("//no translation");
		}
		else
		{
			_translation.keySet().forEach(c -> System.out.printf("// %s --> %s\n", c.getName(), _translation.get(c).getName()));
		}

	}

	private void registerKnownClasses(Class<?> toScan, List<Field> beanFields)
	{
		Predicate<? super Class<?>> _isNotRegistered = i -> !getKnownClasses().contains(i);
		getKnownClasses().add(toScan.getSuperclass());
		Consumer<? super Class<?>> _registerKnownClass = i -> getKnownClasses().add(i);
		Arrays.asList(toScan.getInterfaces()).stream().filter(_isNotRegistered).forEach(_registerKnownClass);
		beanFields.stream().map(Field::getType).filter(_isNotRegistered).forEach(i -> getKnownClasses().add(i));
	}

	// List of all classes referenced in a classe
	Set<Class<?>> myKnownClasses = new HashSet<>();

	Set<Class<?>> getKnownClasses()
	{
		return myKnownClasses;
	}

	// list of shortnames, and the mapped full classname
	// only one class can be mapped to a shortname
	Map<String, String> getShortClassnameMapping(Set<Class<?>> registry)
	{
		Map<String, String> result = new HashMap<>(registry.size());

		registry.forEach(c -> {
			if (!result.containsKey(c.getSimpleName()))
			{
				result.put(c.getSimpleName(), c.getName());

			}
		});
		return result;
	}

	Map<Class<?>, Class<?>> getTranslationMapWhenPojosAreSuffixed(Set<Class<?>> registry, Set<String> sourcePackages,
			String pojoSuffix)
	{
		Map<Class<?>, Class<?>> result = new HashMap<>(registry.size());
		Predicate<String> _isPojo = Pattern.compile("^.+" + pojoSuffix + "$").asPredicate();

		registry.stream().filter(c -> (sourcePackages.contains(c.getPackage().getName()) && _isPojo.test(c.getSimpleName())))
				.forEach(c -> {
					try
					{
						result.put(c, Class.forName(c.getName().substring(0, c.getName().length() - pojoSuffix.length())));
					}
					catch (ClassNotFoundException _exception)
					{
						// TODO Auto-generated catch block
						throw new RuntimeException(_exception);
					}
				});

		return result;
	}

}
