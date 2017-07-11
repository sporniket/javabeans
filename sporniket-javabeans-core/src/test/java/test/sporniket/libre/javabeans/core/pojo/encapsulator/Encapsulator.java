/**
 *
 */
package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
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
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static void main(String[] args) throws ClassNotFoundException
	{
		final Class<?> _toScan = Class.forName("test.sporniket.libre.javabeans.core.pojo.testsuite.SampleBasicBeanRaw");
		System.out.println(_toScan.getSimpleName());
		Arrays.asList(_toScan.getAnnotations()).forEach(a -> System.out.println("  annotation " + a.toString()));

		final List<Field> _fields = Arrays.asList(_toScan.getDeclaredFields());
		for (final Field _field : _fields.stream().filter(IS_PUBLIC_FIELD).collect(Collectors.toList()))
		{
			System.out.println("property : " + _field.getName() + " : " + _field.getType().getName());
			Arrays.asList(_field.getAnnotations()).forEach(a -> System.out.println("  " + "annotation " + a.toString()));
		}

		System.out.println("--- --- ");

		new Encapsulator().processPojoClass(_toScan, System.out);
	}

	// List of all classes referenced in a classe
	final Set<Class<?>> myKnownClasses = new HashSet<>();

	// list of shortnames, and the mapped full classname
	// only one class can be mapped to a shortname
	final Map<String, String> myShortClassnameMapping = new HashMap<>();

	private String computeFieldAccessorSuffix(Field field)
	{
		return field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	}

	private String computeOutputClassname(Class<?> classToOutput, Map<Class<?>, String> translations, Set<String> shortables)
	{
		final String _translatedName = (translations.containsKey(classToOutput))
				? translations.get(classToOutput)
				: classToOutput.getName();
		final String _className = (shortables.contains(_translatedName)) ? getSimpleName(_translatedName) : _translatedName;

		return _className;
	}

	Set<Class<?>> getKnownClasses()
	{
		return myKnownClasses;
	}

	public Map<String, String> getShortClassnameMapping()
	{
		return myShortClassnameMapping;
	}

	private String getSimpleName(String fullClassName)
	{
		return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
	}

	Map<Class<?>, String> getTranslationMapWhenPojosAreSuffixed(Set<Class<?>> registry, Set<String> sourcePackages,
			String pojoSuffix)
	{
		final Map<Class<?>, String> result = new HashMap<>(registry.size());
		final Predicate<String> _isPojo = Pattern.compile("^.+" + pojoSuffix + "$").asPredicate();

		registry.stream().filter(c -> (sourcePackages.contains(c.getPackage().getName()) && _isPojo.test(c.getSimpleName())))
				.forEach(c -> {
					result.put(c, c.getName().substring(0, c.getName().length() - pojoSuffix.length()));
				});

		return result;
	}

	private void outputJavabeanClassDeclaration(Class<?> toScan, PrintStream _out, Map<Class<?>, String> _translation,
			Set<String> _shortables)
	{
		final StringBuilder _classDecl = new StringBuilder("public class ");
		_classDecl.append(computeOutputClassname(toScan, _translation, _shortables));
		if (!Object.class.equals(toScan.getSuperclass()))
		{
			_classDecl.append(" extends ").append(computeOutputClassname(toScan.getSuperclass(), _translation, _shortables));
		}
		final Class<?>[] _interfaces = toScan.getInterfaces();
		if (_interfaces.length > 0)
		{
			for (int _i = 0; _i < _interfaces.length; _i++)
			{
				_classDecl.append((0 == _i) ? " implements " : ", ")
						.append(computeOutputClassname(_interfaces[_i], _translation, _shortables));
			}
		}

		_out.printf("%s\n{\n", _classDecl.toString());

		_out.println();

		_out.printf("    private final %s pojo = new %s() ;\n\n", toScan.getSimpleName(), toScan.getSimpleName());

		_out.println();
	}

	private void processPojoClass(Class<?> toScan, PrintStream _out)
	{
		_out.printf("package %s;\n", toScan.getPackage().getName());

		_out.println();

		// register superclass and interfaces
		final List<Field> _beanFields = Arrays.asList(toScan.getDeclaredFields()).stream().filter(IS_PUBLIC_FIELD)
				.collect(Collectors.toList());

		registerKnownClasses(toScan, _beanFields);

		final Predicate<? super Class<?>> _isNotJavaLangType = c -> !Object.class.getPackage().equals(c.getPackage());
		final Predicate<? super Class<?>> _isNotInSamePackage = c -> !toScan.getPackage().equals(c.getPackage());

		getKnownClasses().stream().filter(_isNotJavaLangType).filter(_isNotInSamePackage).map(c -> c.getName())
				.collect(Collectors.toCollection(TreeSet::new)).forEach(c -> _out.printf("import %s;\n", c));

		_out.println();

		final Map<Class<?>, String> _translation = getTranslationMapWhenPojosAreSuffixed(getKnownClasses(),
				Arrays.asList(toScan.getPackage().getName()).stream().collect(Collectors.toCollection(TreeSet::new)), "Raw");
		if (_translation.isEmpty())
		{
			_out.println("//no translation");
		}
		else
		{
			_out.println("// .--== Type translations ==--.\n//");
			_translation.keySet().forEach(c -> _out.printf("// %s --> %s\n", c.getName(), _translation.get(c)));
		}

		_out.println();

		registerShortClassnameMapping(getKnownClasses());
		// update shortnames mapping
		registerShortClassnameMappingFromString(_translation.values());

		final Map<String, String> _shortNameMapping = getShortClassnameMapping();
		if (_shortNameMapping.isEmpty())
		{
			_out.println("//no shortnames");
		}
		else
		{
			_out.println("// .--== short names mapping ==--.\n//");
			_shortNameMapping.keySet().forEach(c -> _out.printf("// %s --> %s\n", c, _shortNameMapping.get(c)));
		}

		_out.println();

		// reverse short mapping for getting the shortenable names of the class.
		final Set<String> _shortables = new HashSet<>(getShortClassnameMapping().values());

		// ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
		// generate class
		outputJavabeanClassDeclaration(toScan, _out, _translation, _shortables);

		for (final Field _field : _beanFields)
		{
			final String _accessorSuffix = computeFieldAccessorSuffix(_field);
			final String _type = computeOutputClassname(_field.getType(), _translation, _shortables);

			// getter
			_out.printf("    public %s get%s() {return pojo.%s ;}\n", _type, _accessorSuffix, _field.getName());

			// setter
			_out.printf("    public void set%s(%s value) {pojo.%s = value;}\n", _accessorSuffix, _type, _field.getName());

			_out.println();
		}

		_out.println("}\n");

	}

	private void registerKnownClasses(Class<?> toScan, List<Field> beanFields)
	{
		final Predicate<? super Class<?>> _isNotRegistered = i -> !getKnownClasses().contains(i);
		getKnownClasses().add(toScan);
		getKnownClasses().add(toScan.getSuperclass());
		final Consumer<? super Class<?>> _registerKnownClass = i -> getKnownClasses().add(i);
		Arrays.asList(toScan.getInterfaces()).stream().filter(_isNotRegistered).forEach(_registerKnownClass);
		beanFields.stream().map(Field::getType).filter(_isNotRegistered).forEach(i -> getKnownClasses().add(i));
	}

	private void registerShortClassnameMapping(Set<Class<?>> registry)
	{
		registry.forEach(c -> {
			if (!getShortClassnameMapping().containsKey(c.getSimpleName()))
			{
				getShortClassnameMapping().put(c.getSimpleName(), c.getName());

			}
		});
	}

	private void registerShortClassnameMappingFromString(Collection<String> registry)
	{
		registry.forEach(c -> {
			final String _simpleName = getSimpleName(c);
			if (!getShortClassnameMapping().containsKey(_simpleName))
			{
				getShortClassnameMapping().put(_simpleName, c);

			}
		});
	}

}
