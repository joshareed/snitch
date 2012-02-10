package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class TrailingWhitespaceRule extends AbstractRule {

	@Override
	public void check(final File f, final String line, final int i, final SnitchResult results) {
		if (line.length() > 0) {
			char last = line.charAt(line.length() - 1);
			if ((last == ' ') || (last == '\t')) {

				results.addViolation(new Violation(f, i, "Line has trailing whitespace"));
			}
		}
	}
}
