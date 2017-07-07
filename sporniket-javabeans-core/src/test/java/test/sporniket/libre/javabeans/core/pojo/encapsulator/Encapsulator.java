/**
 * 
 */
package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Encapsulate a given Pojo into a Bean.
 * 
 * @author dsporn
 *
 */
public class Encapsulator
{

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException
	{
		// TODO Auto-generated method stub
		Class<?> _toScan = Class.forName("test.sporniket.libre.javabeans.core.pojo.testsuite.SampleBasicBeanRaw");
		System.out.println(_toScan.getSimpleName());

		List<Field> _fields = Arrays.asList(_toScan.getDeclaredFields());
		for (Field _field : _fields.stream().filter(f -> 0 != (f.getModifiers() & Modifier.PUBLIC)).collect(Collectors.toList()))
		{
			System.out.println("property : " + _field.getName() + " : " + _field.getType().getName());
			Arrays.asList(_field.getAnnotations()).forEach(a -> System.out.println("  " + "annotation " + a.toString()));
		}
	}

}
