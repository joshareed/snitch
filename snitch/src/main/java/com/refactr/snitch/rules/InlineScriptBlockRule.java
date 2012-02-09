package com.refactr.snitch.rules;

public class InlineScriptBlockRule extends LineRule {

	@Override
	protected String checkLine(final String line) {
		if ((line.indexOf("<script") > -1) && (line.indexOf("</script>") < 0)) {
			return "Avoid use of inline script blocks";
		}
		return null;
	}
}
