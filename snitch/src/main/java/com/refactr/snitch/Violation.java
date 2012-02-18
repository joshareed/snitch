package com.refactr.snitch;

import java.io.File;

public class Violation implements Comparable<Violation> {
	private final File file;
	private final int line;
	private final String message;
	private final String rule;
	private String blame = null;

	public Violation(final File file, final int line, final String rule, final String message) {
		this(file, line, rule, message, null);
	}

	public Violation(final File file, final int line, final String rule, final String message, final String blame) {
		this.file = file;
		this.line = line;
		this.rule = rule;
		this.message = message;
		this.blame = blame;
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

	public String getBlame() {
		return blame;
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

	public void setBlame(final String blame) {
		this.blame = blame;
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
