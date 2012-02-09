package com.refactr.snitch.rules;

public class TrailingWhitespaceRule extends LineRule implements Rule {

	@Override
	protected String checkLine(final String line) {
		char last = line.charAt(line.length() - 1);
		if ((last == ' ') || (last == '\t')) {
			return "Line has trailing whitespace";
		}
		return null;
	}
}
