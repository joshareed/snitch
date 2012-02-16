package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class EmbeddedScriptBlockRule extends AbstractRule {
	private static final String MSG = "Avoid use of embedded script blocks";
	private static final String RULE = EmbeddedScriptBlockRule.class.getSimpleName().replace("Rule", "");

	@Override
	public void check(final File f, final String line, final int i, final SnitchResult results) {
		if ((line.indexOf("<script") > -1) && (line.indexOf("</script>") < 0)) {
			results.addViolation(new Violation(f, i, RULE, MSG));
		}
	}
}
