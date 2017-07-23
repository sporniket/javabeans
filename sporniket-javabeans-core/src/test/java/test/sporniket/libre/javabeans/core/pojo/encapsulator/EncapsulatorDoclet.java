package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.core.pojo.encapsulator.ClassDocUtils;
import com.sporniket.libre.javabeans.core.pojo.encapsulator.ClassUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;

public class EncapsulatorDoclet
{
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
		Main.execute(_args);
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

	private void processPojoClass(ClassDoc toScan, PrintStream _out)
	{
		_out.printf("package %s;\n", toScan.containingPackage().name());

		_out.println();

		final Set<String> _knownClasses = new TreeSet<>();
		ClassDocUtils.updateKnowClasses(_knownClasses, toScan);

		final Predicate<? super String> _isNotJavaLangType = c -> !Object.class.getPackage().getName()
				.equals(ClassUtils.getPackageName(c));
		final Predicate<? super String> _isNotInSamePackage = c -> !toScan.containingPackage().name()
				.equals(ClassUtils.getPackageName(c));

		_knownClasses.stream().filter(_isNotJavaLangType).filter(_isNotInSamePackage).collect(Collectors.toCollection(TreeSet::new))
				.forEach(c -> _out.printf("import %s;\n", c));

		_out.println();

		final Map<String, String> _translation = ClassDocUtils.getTranslationMapWhenPojosAreSuffixed(_knownClasses,
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
		ClassUtils.updateShortClassnameMappingFromClassnames(_shortNameMapping, _knownClasses);
		ClassUtils.updateShortClassnameMappingFromClassnames(_shortNameMapping, _translation.values());

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

	}
}
