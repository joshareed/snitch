package com.refactr.snitch;

import java.io.File;

public class Violation {
	private final int endLine;
	private final File file;
	private final String message;
	private final int startLine;

	public Violation(final File file, final String message) {
		this(file, message, -1, -1);
	}

	public Violation(final File file, final String message, final int line) {
		this(file, message, line, line);
	}

	public Violation(final File file, final String message, final int startLine, final int endLine) {
		this.file = file;
		this.message = message;
		this.startLine = startLine;
		this.endLine = endLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public File getFile() {
		return file;
	}

	public String getMessage() {
		return message;
	}

	public int getStartLine() {
		return startLine;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(file.getName());
		if (startLine != -1) {
			buf.append(":");
			buf.append(startLine);
		}
		if ((endLine != -1) && (endLine != startLine)) {
			buf.append("-");
			buf.append(endLine);
		}
		buf.append("    ");
		buf.append(message);
		return buf.toString();
	}
}
