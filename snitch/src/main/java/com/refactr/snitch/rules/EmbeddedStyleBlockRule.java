package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.FileTypes;
import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class EmbeddedStyleBlockRule extends AbstractRule {
	private static final String MSG = "Avoid use of embedded style blocks";
	private static final String RULE = EmbeddedStyleBlockRule.class.getSimpleName().replace("Rule", "");

	@Override
	public boolean beforeFile(final File file, final SnitchResult results) {
		return is(file, FileTypes.MARKUP);
	}

	@Override
	public void check(final File f, final String line, final int i, final SnitchResult results) {
		if ((line.indexOf("<style") > -1) && (line.indexOf("</style>") < 0)) {
			results.addViolation(new Violation(f, i, RULE, MSG));
		}
	}
}
