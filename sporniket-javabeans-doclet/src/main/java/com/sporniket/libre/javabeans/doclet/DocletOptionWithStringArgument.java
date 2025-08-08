package com.sporniket.libre.javabeans.doclet;

import static javax.tools.Diagnostic.Kind.ERROR;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import jdk.javadoc.doclet.Reporter;

public class DocletOptionWithStringArgument extends DocletOptionBase {

	protected DocletOptionWithStringArgument(List<String> names, String description, DocletOptions recipient,
			Reporter reporter) {
		super(names, description, recipient, reporter);
	}

	protected DocletOptionWithStringArgument(String name, String description, DocletOptions recipient,
			Reporter reporter) {
		super(name, description, recipient, reporter);
	}

	@Override
	public int getArgumentCount() {
		return 1;
	}

	@Override
	public String getParameters() {
		return "<string>";
	}

	@Override
	public boolean process(String option, List<String> arguments) {
		if (null == arguments || arguments.isEmpty()) {
			getReporter().print(ERROR, "option.processing.requires.argument" + option);
			return false;
		}		return normalizeOption(option)//
				.map(o -> applyToRecipient(o, arguments.getFirst()))//
				.orElse(false);
	}

	private Optional<String> normalizeOption(String option) {
		for (int i = 0; i < option.length(); i++) {
			if ('-' != option.charAt(i)) {
				return Optional.of(option.substring(i));
			}
		}
		getReporter().print(ERROR, "wrong.processed.option.name:" + option);
		return Optional.empty();
	}

	private boolean applyToRecipient(String field, String value) {
		try {
			final Field _targetField = getRecipient().getClass().getDeclaredField(field);
			_targetField.setAccessible(true); // direct application to avoid computing method names
			_targetField.set(getRecipient(), value);
			return true;
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			getReporter().print(ERROR, "cannot.apply.option:" + field);
			return false;
		}
	}

}
