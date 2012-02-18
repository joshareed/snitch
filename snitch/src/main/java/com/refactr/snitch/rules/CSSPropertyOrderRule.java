package com.refactr.snitch.rules;

import java.io.File;
import java.util.List;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class CSSPropertyOrderRule extends AbstractCSSRule {
	private enum Type {
		BOX, BORDER, BACKGROUND, TEXT, OTHER;
	}

	private static final String MSG = "CSS property order: ";
	private static final String RULE = CSSPropertyOrderRule.class.getSimpleName().replace("Rule", "");

	protected Type getType(final String prop) {
		// get our prefix
		String prefix = prop.toLowerCase();
		if (prefix.startsWith("-")) {
			prefix = prefix.substring(prefix.indexOf('-', 1) + 1);
		}
		int i = prefix.indexOf('-');
		if (i >= 0) {
			prefix = prefix.substring(0, i);
		}

		if (in(prefix, "display", "float", "position", "left", "top", "right", "bottom", "width", "height", "margin",
				"padding", "box", "min", "max", "z", "overflow", "clear")) {
			return Type.BOX;
		} else if (in(prefix, "border", "outline")) {
			return Type.BORDER;
		} else if (in(prefix, "background", "opacity")) {
			return Type.BACKGROUND;
		} else if (in(prefix, "font", "text", "letter", "color", "src", "line", "vertical", "quotes", "content")) {
			return Type.TEXT;
		} else {
			return Type.OTHER;
		}
	}

	@Override
	protected void handleCSS(final File file, final int line, final List<String> selectors,
			final List<String> properties, final SnitchResult results) {
		Type last = Type.BOX;
		for (String p : properties) {
			String[] split = p.split(":");
			if (split.length > 1) {
				Type type = getType(split[0]);
				if (type.ordinal() < last.ordinal()) {
					results.addViolation(new Violation(file, line, RULE, MSG + type.name()
							+ " properties should appear before " + last.name() + " properties [" + p + "]"));
				} else {
					last = type;
				}
			}
		}
	}

	protected boolean in(final String str, final String... list) {
		for (String s : list) {
			if (s.equals(str)) {
				return true;
			}
		}
		return false;
	}
}
