package com.refactr.snitch;

import java.io.File;

public class Violation implements Comparable<Violation> {
	private final File file;
	private final int line;
	private final String message;
	private final String rule;

	public Violation(final File file, final int line, final String rule, final String message) {
		this.file = file;
		this.line = line;
		this.rule = rule;
		this.message = message;
	}

	@Override
	public int compareTo(final Violation o) {
		int v = file.compareTo(o.getFile());
		if (v == 0) {
			if (line < o.getLine()) {
				return -1;
			} else if (line > o.getLine()) {
				return 1;
			} else {
				return rule.compareTo(o.getRule());
			}
		} else {
			return v;
		}
	}

	public File getFile() {
		return file;
	}

	public int getLine() {
		return line;
	}

	public String getMessage() {
		return message;
	}

	public String getRule() {
		return rule;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(file.getName());
		buf.append(":");
		buf.append(line);
		buf.append("  [");
		buf.append(rule);
		buf.append("]  ");
		buf.append(message);
		return buf.toString();
	}
}
