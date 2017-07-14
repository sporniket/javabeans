package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
	public static boolean start(RootDoc root)
	{
		ClassDoc[] classes = root.classes();
		for (int i = 0; i < classes.length; ++i)
		{
			System.out.println(classes[i]);
			System.out.println("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ");
			new EncapsulatorDoclet().processPojoClass(classes[i], System.out);
		}
		return true;
	}

	private void processPojoClass(ClassDoc toScan, PrintStream _out)
	{
		_out.printf("package %s;\n", toScan.containingPackage().name());

		_out.println();

		List<String> _knownClasses = new ArrayList<>();
		ClassDocUtils.updateKnowClasses(_knownClasses, toScan);

		final Predicate<? super String> _isNotJavaLangType = c -> !Object.class.getPackage().equals(ClassUtils.getPackageName(c));
		final Predicate<? super String> _isNotInSamePackage = c -> !toScan.containingPackage().equals(ClassUtils.getPackageName(c));

		_knownClasses.stream().filter(_isNotJavaLangType).filter(_isNotInSamePackage)
				.collect(Collectors.toCollection(TreeSet::new)).forEach(c -> _out.printf("import %s;\n", c));
	}

	public static void main(String[] args)
	{
		String[] _args =
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
}
