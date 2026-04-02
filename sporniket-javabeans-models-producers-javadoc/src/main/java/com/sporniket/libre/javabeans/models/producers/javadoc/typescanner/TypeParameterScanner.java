package com.sporniket.libre.javabeans.models.producers.javadoc.typescanner;

import java.util.List;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

public class TypeParameterScanner
{
	private final Types myTypeUtils;

	public TypeParameterScanner(final Types typeUtils)
	{
		super();
		myTypeUtils = typeUtils;
	}

	public Types getTypeUtils()
	{
		return myTypeUtils;
	}

	public TypeParameterCollection scan(final DeclaredType main)
	{
		final TypeParameterCollection result = new TypeParameterCollection();
		main.getTypeArguments().stream() //
				.map(this::dumpFullType) //
				.forEach(ft -> result.append(ft, ft));

		return result;
	}

	String dumpFullType(final TypeMirror type)
	{
		switch (type.getKind())
		{
			case DECLARED:
				return dumpFullType((DeclaredType) type);
			case TYPEVAR:
				return dumpFullType((TypeVariable) type);
			case WILDCARD:
				return dumpFullType((WildcardType) type);
			case ARRAY:
				return dumpFullType((ArrayType) type);
			default:
				return type.toString();
		}
	}

	String dumpFullType(final DeclaredType declared)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(declared.asElement().toString()); // raw type name
		final List<? extends TypeMirror> args = declared.getTypeArguments();
		if (!args.isEmpty())
		{
			sb.append("<");
			for (int i = 0; i < args.size(); i++)
			{
				if (i > 0)
				{
					sb.append(", ");
				}
				sb.append(dumpFullType(args.get(i)));
			}
			sb.append(">");
		}
		return sb.toString();
	}

	String dumpFullType(final WildcardType wc)
	{
		final StringBuilder wcsb = new StringBuilder("?");
		if (wc.getExtendsBound() != null)
		{
			wcsb.append(" extends ").append(dumpFullType(wc.getExtendsBound()));
		}
		else if (wc.getSuperBound() != null)
		{
			wcsb.append(" super ").append(dumpFullType(wc.getSuperBound()));
		}
		return wcsb.toString();
	}

	String dumpFullType(final ArrayType a)
	{
		return dumpFullType(a.getComponentType()) + "[]";
	}

	String dumpFullType(final TypeVariable t)
	{
		return t.asElement().toString();
	}

}
