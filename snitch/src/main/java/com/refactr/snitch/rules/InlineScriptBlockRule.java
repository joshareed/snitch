package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class InlineScriptBlockRule extends LineRule {

	@Override
	public void check(final File f, final String line, final int i, final SnitchResult results) {
		if ((line.indexOf("<script") > -1) && (line.indexOf("</script>") < 0)) {
			results.addViolation(new Violation(f, i, "Avoid use of inline script blocks"));
		}
	}
}
