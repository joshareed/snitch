package com.refactr.snitch.rules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.refactr.snitch.SnitchResult;

public abstract class AbstractCSSRule extends AbstractRule {
	protected StringBuilder buffer = new StringBuilder();
	protected boolean inComment = false;
	protected boolean inRule = false;
	protected char last = '\n';
	protected List<String> properties = new ArrayList<String>();
	protected int ruleLine = -1;
	protected List<String> selectors = new ArrayList<String>();

	@Override
	public boolean beforeFile(final File file, final SnitchResult results) {
		// clear state
		last = '\n';
		inComment = false;
		inRule = false;
		ruleLine = -1;
		buffer = new StringBuilder();

		// only parse CSS files
		return is(file, ".css");
	}

	@Override
	public void check(final File file, final String line, final int num, final SnitchResult results) {
		for (char c : line.toCharArray()) {
			switch (c) {
			case '/': // maybe ending a comment
				if (inComment && (last == '*')) {
					inComment = false;
				} else if (!inComment) {
					buffer.append(c);
				}
				break;
			case '*': // maybe starting a comment
				if (!inComment && (last == '/')) {
					inComment = true;
					buffer.deleteCharAt(buffer.length() - 1);
				} else if (!inComment) {
					buffer.append(c);
				}
				break;
			case '{': // starting a rule
				if (!inComment) {
					inRule = true;
					ruleLine = num;

					// split our selectors
					selectors.clear();
					for (String s : buffer.toString().split(",")) {
						s = s.trim();
						if (!"".equals(s)) {
							selectors.add(s);
						}
					}
					buffer = new StringBuilder();
				}
				break;
			case '}': // ending a rule
				if (!inComment) {
					inRule = false;

					// split our properties
					properties.clear();
					for (String s : buffer.toString().split(";")) {
						s = s.trim();
						if (!"".equals(s)) {
							properties.add(s);
						}
					}
					buffer = new StringBuilder();

					handleCSS(ruleLine, selectors, properties, results);
				}
				break;
			default:
				if (!inComment) {
					buffer.append(c);
				}
			}
			last = c;
		}
		last = '\n';
	}

	protected abstract void handleCSS(final int line, final List<String> selectors, final List<String> properties,
			final SnitchResult results);
}
