package test.sporniket.libre.javabeans.mockjavadoc;

import static com.sporniket.strings.StringPredicates.IS_NOT_BLANK;
import static java.util.stream.Collectors.toList;
import static test.sporniket.libre.javabeans.mockjavadoc.MockFactory.CLASSDOC_FROM_QUALIFIED_NAME;
import static test.sporniket.libre.javabeans.mockjavadoc.MockFactory.PACKAGEDOC_FROM_QUALIFIED_NAME;
import static test.sporniket.libre.javabeans.mockjavadoc.MockFactory.TYPE_FROM_QUALIFIED_NAME;
import static test.sporniket.libre.javabeans.mockjavadoc.QualifiedNameUtils.CONTAINER_FROM_QUALIFIED_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Type;

public class MockSetupLoader
{
	static class SetupStruct
	{
		public ClassDocMock[] classes;

		public FieldDocMock[] fields;
	}

	public MockSetup load(InputStream in) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper _mapper = new ObjectMapper();
		return load(_mapper.readValue(in, SetupStruct.class));
	}

	MockSetup load(SetupStruct input)
	{
		final Map<String, Type> types = new HashMap<>();
		final Map<String, FieldDoc> fields = new HashMap<>();
		final Map<String, ClassDocMock> classes = new HashMap<>();
		final Map<String, PackageDoc> packages = new HashMap<>();

		// registering fields and types
		for (FieldDocMock field : input.fields)
		{
			if (!types.containsKey(field.getRefType()))
			{
				types.put(field.getRefType(), TYPE_FROM_QUALIFIED_NAME.build(field.getRefType()));
			}
			fields.put(field.qualifiedName(), (FieldDoc) new FieldDocMockModel_Builder(field)//
					.withValType(types.get(field.getRefType()))//
					.done());
		}

		// processing classes -- first pass : external references and registering
		for (ClassDocMock classdoc : input.classes)
		{
			final String _packageName = CONTAINER_FROM_QUALIFIED_NAME.transform(classdoc.qualifiedName());
			if (!packages.containsKey(_packageName))
			{
				packages.put(_packageName, PACKAGEDOC_FROM_QUALIFIED_NAME.build(_packageName));
			}
			// linking to fields, missing fields are ignored.
			final List<FieldDoc> _valFields = classdoc.getRefFields()//
					.parallelStream()//
					.filter(f -> fields.containsKey(f))//
					.map(f -> fields.get(f)).collect(toList());

			classes.put(classdoc.qualifiedName(), (ClassDocMock) new ClassDocMockModel_Builder(classdoc)//
					.withValFieldDocs(_valFields)//
					.withValContainingPackage(packages.get(_packageName))//
					.done());

		}

		// processing classes -- second pass : cross references
		final Map<String, ClassDocMock> classesDone = new HashMap<>();
		for (Map.Entry<String, ClassDocMock> classdocEntry : classes.entrySet())
		{
			// cross reference superclasses
			ClassDocMock _classdoc = classdocEntry.getValue();
			load_crossReference_superclass(classes, classesDone, _classdoc);

			// final registration
			classesDone.put(classdocEntry.getKey(), _classdoc);
		}

		return new MockSetup_Builder()//
				.withTypes(new HashMap<>(types))//
				.withFields(new HashMap<>(fields))//
				.withClasses(new HashMap<>(classesDone))//
				.withPackages(new HashMap<>(packages))//
				.done();
	}

	private void load_crossReference_superclass(final Map<String, ClassDocMock> source, final Map<String, ClassDocMock> recipient,
			ClassDocMock classdoc)
	{
		if (IS_NOT_BLANK.test(classdoc.getRefSuperclass()))
		{
			if (!source.containsKey(classdoc.getRefSuperclass()))
			{
				if (!recipient.containsKey(classdoc.getRefSuperclass()))
				{
					recipient.put(classdoc.getRefSuperclass(),
							(ClassDocMock) CLASSDOC_FROM_QUALIFIED_NAME.build(classdoc.getRefSuperclass()));
				}
				classdoc.setValSuperclass(recipient.get(classdoc.getRefSuperclass()));
			}
			else
			{
				classdoc.setValSuperclass(source.get(classdoc.getRefSuperclass()));
			}
		}
	}

}
