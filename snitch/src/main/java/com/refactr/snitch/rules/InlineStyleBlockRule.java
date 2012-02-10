package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class InlineStyleBlockRule extends AbstractRule {

	@Override
	public void check(final File f, final String line, final int i, final SnitchResult results) {
		if ((line.indexOf("<style") > -1) && (line.indexOf("</style>") < 0)) {
			results.addViolation(new Violation(f, i, "Avoid use of inline style blocks"));
		}
	}
}
