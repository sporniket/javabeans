package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.Doc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;

public class DocMock extends DocMockModel implements Doc
{

	@Override
	public String commentText()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tag[] firstSentenceTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRawCommentText()
	{
		return String.join("\n", getValRawComment());
	}

	@Override
	public Tag[] inlineTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAnnotationType()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAnnotationTypeElement()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConstructor()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnum()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnumConstant()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isError()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isException()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isField()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIncluded()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInterface()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMethod()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOrdinaryClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String name()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourcePosition position()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeeTag[] seeTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRawCommentText(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Tag[] tags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag[] tags(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
