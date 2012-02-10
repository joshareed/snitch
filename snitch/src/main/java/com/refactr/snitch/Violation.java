package com.refactr.snitch;

import java.io.File;

public class Violation {
	private final File file;
	private final int line;
	private final String message;

	public Violation(final File file, final int line, final String message) {
		this.file = file;
		this.message = message;
		this.line = line;
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

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(file.getName());
		buf.append(":");
		buf.append(line);
		buf.append("    ");
		buf.append(message);
		return buf.toString();
	}
}
