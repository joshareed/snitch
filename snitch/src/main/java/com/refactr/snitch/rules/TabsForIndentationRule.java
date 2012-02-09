package com.refactr.snitch.rules;

public class TabsForIndentationRule extends LineRule {

	@Override
	protected String checkLine(final String line) {
		for (int j = 0; j < line.length(); j++) {
			char c = line.charAt(j);
			if (c == ' ') {
				return "Line uses spaces for indentation";
			} else if (c != '\t') {
				return null;
			}
		}
		return null;
	}
}
