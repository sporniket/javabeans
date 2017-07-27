package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import static com.sporniket.libre.javabeans.core.pojo.encapsulator.ClassDocUtils.*;
import static com.sporniket.libre.javabeans.core.pojo.encapsulator.ClassUtils.*;
import static com.sporniket.libre.javabeans.core.pojo.encapsulator.FieldDocUtils.getPublicDeclaredFields;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

import com.sporniket.libre.javabeans.core.pojo.encapsulator.FieldUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

public class EncapsulatorDoclet
{

	/**
	 * Supports generics and annotations.
	 * 
	 * @return {@link LanguageVersion#JAVA_1_5}
	 */
	public static LanguageVersion languageVersion()
	{
		return LanguageVersion.JAVA_1_5;
	}

	public static void main(String[] args)
	{
		final String[] _args =
		{
				"-sourcepath",
				"/home/dsporn/dev/00-src/javabeans/sporniket-javabeans-core/src/test/java",
				"-private",
				"-doclet",
				"test.sporniket.libre.javabeans.core.pojo.encapsulator.EncapsulatorDoclet",
				"test.sporniket.libre.javabeans.core.pojo.testsuite"

		};

		DocumentationTool javadoc = ToolProvider.getSystemDocumentationTool();
		javadoc.run(System.in, System.out, System.err, _args);
	}

	public static boolean start(RootDoc root)
	{
		final ClassDoc[] classes = root.classes();
		for (int i = 0; i < classes.length; ++i)
		{
			System.out.println("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ");
			System.out.println("//" + classes[i]);
			new EncapsulatorDoclet().processPojoClass(classes[i], System.out);
		}
		return true;
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
	private void outputJavabean(ClassDoc rawClass, PrintStream out, final Map<String, String> translations,
			final Set<String> shortables)
	{
		outputJavabean__classBegin(rawClass, out, translations, shortables);

		getPublicDeclaredFields(rawClass).forEach(_field -> {
			outputJavabean__property(_field, out, translations, shortables);
		});

		outputJavabean__classEnd(rawClass, out, translations, shortables);
	}

	private void outputJavabean__classBegin(ClassDoc toScan, PrintStream _out, Map<String, String> translations,
			Set<String> shortables)
	{
		final StringBuilder _classDecl = new StringBuilder("public class ");
		outputClassName__classDeclaration(_classDecl, toScan, translations, shortables);

		final String _supername = toScan.superclass().qualifiedName();
		if (!Object.class.getName().equals(_supername))
		{
			_classDecl.append(" extends ").append(computeOutputClassname(_supername, translations, shortables));
		}
		final ClassDoc[] _interfaces = toScan.interfaces();
		if (_interfaces.length > 0)
		{
			for (int _i = 0; _i < _interfaces.length; _i++)
			{
				_classDecl.append((0 == _i) ? " implements " : ", ")
						.append(computeOutputClassname(_interfaces[_i].name(), translations, shortables));
			}
		}

		_out.printf(_classDecl.append("\n{\n").toString());

		_out.println();

		StringBuilder _pojoDecl = new StringBuilder("    private final ");
		outputClassName__pojoType(_pojoDecl, toScan, translations, shortables);
		_pojoDecl.append(" pojo = new ");
		outputClassName__pojoInstanciation(_pojoDecl, toScan, translations, shortables);

		_out.printf(_pojoDecl.append("() ;\n\n").toString());

		_out.println();
	}

	private void outputJavabean__classEnd(ClassDoc toScan, PrintStream _out, Map<String, String> _translation,
			Set<String> _shortables)
	{
		_out.println("}\n");
	}

	private void outputJavabean__property(final FieldDoc field, PrintStream out, final Map<String, String> translations,
			final Set<String> shortables)
	{
		final String _accessorSuffix = FieldUtils.computeFieldAccessorSuffix(field.name());
		String _type = computeOutputType(field.type(), translations, shortables);

		// getter
		out.printf("    public %s get%s() {return pojo.%s ;}\n", _type, _accessorSuffix, field.name());

		// setter
		out.printf("    public void set%s(%s value) {pojo.%s = value;}\n", _accessorSuffix, _type, field.name());

		out.println();
	}

	private void processPojoClass(ClassDoc toScan, PrintStream _out)
	{
		_out.printf("package %s;\n", toScan.containingPackage().name());

		_out.println();

		final Set<String> _knownClasses = new TreeSet<>();
		updateKnowClasses(_knownClasses, toScan);

		final Predicate<? super String> _isNotJavaLangType = c -> !Object.class.getPackage().getName().equals(getPackageName(c));
		final Predicate<? super String> _isNotInSamePackage = c -> !toScan.containingPackage().name().equals(getPackageName(c));

		_knownClasses.stream().filter(_isNotJavaLangType).filter(_isNotInSamePackage).collect(Collectors.toCollection(TreeSet::new))
				.forEach(c -> _out.printf("import %s;\n", c));

		_out.println();

		final Map<String, String> _translation = getTranslationMapWhenPojosAreSuffixed(_knownClasses,
				Arrays.asList(toScan.containingPackage().name()).stream().collect(Collectors.toCollection(TreeSet::new)), "Raw");
		if (_translation.isEmpty())
		{
			_out.println("//no translation");
		}
		else
		{
			_out.println("// .--== Type translations ==--.\n//");
			_translation.keySet().forEach(c -> _out.printf("// %s\n//     --> %s\n", c, _translation.get(c)));
		}

		_out.println();

		final Map<String, String> _shortNameMapping = new HashMap<>(_knownClasses.size() + _translation.size());
		updateShortClassnameMappingFromClassnames(_shortNameMapping, _knownClasses);
		updateShortClassnameMappingFromClassnames(_shortNameMapping, _translation.values());

		if (_shortNameMapping.isEmpty())
		{
			_out.println("//no shortnames");
		}
		else
		{
			_out.println("// .--== short names mapping ==--.\n//");
			_shortNameMapping.keySet().forEach(c -> _out.printf("// %s\n//     --> %s\n", c, _shortNameMapping.get(c)));
		}

		_out.println();

		// reverse short mapping for getting the shortenable names of the class.
		final Set<String> _shortables = new HashSet<>(_shortNameMapping.values());

		// ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
		// generate class
		outputJavabean(toScan, _out, _translation, _shortables);
	}
}
