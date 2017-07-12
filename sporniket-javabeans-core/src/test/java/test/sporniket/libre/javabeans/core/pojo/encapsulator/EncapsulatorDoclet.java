package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import com.sun.javadoc.*;
import com.sun.tools.javadoc.Main;

public class EncapsulatorDoclet
{
	public static boolean start(RootDoc root)
	{
		ClassDoc[] classes = root.classes();
		for (int i = 0; i < classes.length; ++i)
		{
			System.out.println(classes[i]);
		}
		return true;
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
