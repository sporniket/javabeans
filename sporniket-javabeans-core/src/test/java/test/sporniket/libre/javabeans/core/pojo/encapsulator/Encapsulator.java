/**
 *
 */
package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.core.pojo.encapsulator.ClassUtils;
import com.sporniket.libre.javabeans.core.pojo.encapsulator.FieldUtils;

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

		for (final Field _field : FieldUtils.getPublicDeclaredFields(_toScan))
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

	Set<Class<?>> getKnownClasses()
	{
		return myKnownClasses;
	}

	public Map<String, String> getShortClassnameMapping()
	{
		return myShortClassnameMapping;
	}

	/**
	 * Generate a Javabean defined by encapsulating a pojo.
	 * 
	 * @param rawClass
	 *            the pojo to encapsulate into a javabean.
	 * @param out
	 *            the {@link PrintStream} to generate the definition into.
	 * @param translations
	 *            a translation table to get the javabeans type from pojo types (since the goal is to convert a set of related pojo
	 *            into a set of related javabeans).
	 * @param shortables
	 *            the list of classes that can be shortened (use the simple name instead of the full classname after the import
	 *            declaration)
	 */
	private void outputJavabean(Class<?> rawClass, PrintStream out, final Map<String, String> translations,
			final Set<String> shortables)
	{
		outputJavabean__classBegin(rawClass, out, translations, shortables);

		FieldUtils.getPublicDeclaredFields(rawClass).forEach(_field -> {
			outputJavabean__property(_field, out, translations, shortables);
		});

		outputJavabean__classEnd(rawClass, out, translations, shortables);
	}

	private void outputJavabean__classBegin(Class<?> toScan, PrintStream _out, Map<String, String> _translation,
			Set<String> _shortables)
	{
		final StringBuilder _classDecl = new StringBuilder("public class ");
		_classDecl.append(ClassUtils.computeOutputClassname(toScan, _translation, _shortables));
		if (!Object.class.equals(toScan.getSuperclass()))
		{
			_classDecl.append(" extends ")
					.append(ClassUtils.computeOutputClassname(toScan.getSuperclass(), _translation, _shortables));
		}
		final Class<?>[] _interfaces = toScan.getInterfaces();
		if (_interfaces.length > 0)
		{
			for (int _i = 0; _i < _interfaces.length; _i++)
			{
				_classDecl.append((0 == _i) ? " implements " : ", ")
						.append(ClassUtils.computeOutputClassname(_interfaces[_i], _translation, _shortables));
			}
		}

		_out.printf("%s\n{\n", _classDecl.toString());

		_out.println();

		_out.printf("    private final %s pojo = new %s() ;\n\n", toScan.getSimpleName(), toScan.getSimpleName());

		_out.println();
	}

	private void outputJavabean__classEnd(Class<?> toScan, PrintStream _out, Map<String, String> _translation,
			Set<String> _shortables)
	{
		_out.println("}\n");
	}

	private void outputJavabean__property(final Field field, PrintStream out, final Map<String, String> translation,
			final Set<String> shortables)
	{
		final String _accessorSuffix = FieldUtils.computeFieldAccessorSuffix(field);
		final String _type = ClassUtils.computeOutputClassname(field.getType(), translation, shortables);

		// getter
		out.printf("    public %s get%s() {return pojo.%s ;}\n", _type, _accessorSuffix, field.getName());

		// setter
		out.printf("    public void set%s(%s value) {pojo.%s = value;}\n", _accessorSuffix, _type, field.getName());

		out.println();
	}

	private void processPojoClass(Class<?> toScan, PrintStream _out)
	{
		_out.printf("package %s;\n", toScan.getPackage().getName());

		_out.println();

		ClassUtils.updateKnownClasses(getKnownClasses(), toScan);

		final Predicate<? super Class<?>> _isNotJavaLangType = c -> !Object.class.getPackage().equals(c.getPackage());
		final Predicate<? super Class<?>> _isNotInSamePackage = c -> !toScan.getPackage().equals(c.getPackage());

		getKnownClasses().stream().filter(_isNotJavaLangType).filter(_isNotInSamePackage).map(c -> c.getName())
				.collect(Collectors.toCollection(TreeSet::new)).forEach(c -> _out.printf("import %s;\n", c));

		_out.println();

		final Map<String, String> _translation = ClassUtils.getTranslationMapWhenPojosAreSuffixed(getKnownClasses(),
				Arrays.asList(toScan.getPackage().getName()).stream().collect(Collectors.toCollection(TreeSet::new)), "Raw");
		if (_translation.isEmpty())
		{
			_out.println("//no translation");
		}
		else
		{
			_out.println("// .--== Type translations ==--.\n//");
			_translation.keySet().forEach(c -> _out.printf("// %s --> %s\n", c, _translation.get(c)));
		}

		_out.println();

		// update shortnames mapping
		ClassUtils.updateShortClassnameMappingFromClasses(getShortClassnameMapping(), getKnownClasses());
		ClassUtils.updateShortClassnameMappingFromStrings(getShortClassnameMapping(), _translation.values());

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
		outputJavabean(toScan, _out, _translation, _shortables);

	}
}
