package com.refactr.snitch.rules;

public class InlineStyleBlockRule extends LineRule {

	@Override
	protected String checkLine(final String line) {
		if ((line.indexOf("<style") > -1) && (line.indexOf("</style>") < 0)) {
			return "Avoid use of inline style blocks";
		}
		return null;
	}

}
