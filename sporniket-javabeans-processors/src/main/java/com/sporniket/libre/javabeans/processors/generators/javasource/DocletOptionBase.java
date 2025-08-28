package com.sporniket.libre.javabeans.processors.generators.javasource;

import java.util.List;

import jdk.javadoc.doclet.Doclet.Option;
import jdk.javadoc.doclet.Reporter;

public abstract class DocletOptionBase implements Option{
	
	private final List<String> myNames ;
	private final String myDescription ;
	private final DocletOptions myRecipient ;
	private final Reporter myReporter ;


	public DocletOptionBase(List<String> names, String description, DocletOptions recipient, Reporter reporter) {
		super();
		this.myNames = names;
		this.myDescription = description;
		this.myRecipient = recipient ;
		this.myReporter = reporter ;
	}
	
	public DocletOptionBase(String name, String description, DocletOptions recipient, Reporter reporter) {
		this(List.of(name), description, recipient, reporter);
	}
	
	// ===============================[ Doclet options ]==================================

	@Override
	public String getDescription() {
		return myDescription;
	}

	@Override
	public Kind getKind() {
		return Kind.STANDARD;
	}

	@Override
	public List<String> getNames() {
		return myNames;
	}

	// ===============================[ Properties ]==================================

	public DocletOptions getRecipient() {
		return myRecipient;
	}

	public Reporter getReporter() {
		return myReporter;
	}

	
}
