package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class InlineStyleRule extends AbstractRule {
	private static final String MSG = "Avoid use of inline styles on elements";
	private static final String RULE = InlineStyleRule.class.getSimpleName().replace("Rule", "");

	@Override
	public void check(final File f, final String line, final int i, final SnitchResult results) {
		if (line.indexOf(" style=") > -1) {
			results.addViolation(new Violation(f, i, RULE, MSG));
		}
	}
}
