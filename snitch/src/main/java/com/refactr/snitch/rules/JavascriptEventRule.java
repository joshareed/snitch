package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class JavascriptEventRule extends AbstractRule {
	private static final String[] EVENTS = new String[] { "onabort", "onbeforeonload", "onbeforeunload", "onblur",
			"onchange", "onclick", "oncontextmenu", "ondblclick", "ondrag", "ondrop", "onfocus", "onkey", "onload",
			"onmessage", "onmouse", "onreset", "onresize", "onscroll", "onselect", "onsubmit", "onunload" };
	private static final String MSG = "Avoid hooking directly into javascript events";
	private static final String RULE = JavascriptEventRule.class.getSimpleName().replace("Rule", "");

	@Override
	public void check(final File f, final String line, final int i, final SnitchResult results) {
		String clean = line.toLowerCase().trim();
		if (clean.indexOf("on") > -1) {
			for (String e : EVENTS) {
				if (clean.indexOf(e) > -1) {
					results.addViolation(new Violation(f, i, RULE, MSG));
					return;
				}
			}
		}
	}
}
