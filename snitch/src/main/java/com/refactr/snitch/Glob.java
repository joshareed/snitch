package com.refactr.snitch;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Glob {
	protected final Pattern pattern;

	public Glob(final String glob) {
		String line = glob.trim();
		int strLen = line.length();

		StringBuilder sb = new StringBuilder(strLen);
		boolean escaping = false;
		int inCurlies = 0;
		for (char currentChar : line.toCharArray()) {
			switch (currentChar) {
			case '*':
				if (escaping) {
					sb.append("\\*");
				} else {
					sb.append(".*");
				}
				escaping = false;
				break;
			case '?':
				if (escaping) {
					sb.append("\\?");
				} else {
					sb.append('.');
				}
				escaping = false;
				break;
			case '.':
			case '(':
			case ')':
			case '+':
			case '|':
			case '^':
			case '$':
			case '@':
			case '%':
				sb.append('\\');
				sb.append(currentChar);
				escaping = false;
				break;
			case '\\':
				if (escaping) {
					sb.append("\\\\");
					escaping = false;
				} else {
					escaping = true;
				}
				break;
			case '{':
				if (escaping) {
					sb.append("\\{");
				} else {
					sb.append('(');
					inCurlies++;
				}
				escaping = false;
				break;
			case '}':
				if ((inCurlies > 0) && !escaping) {
					sb.append(')');
					inCurlies--;
				} else if (escaping) {
					sb.append("\\}");
				} else {
					sb.append("}");
				}
				escaping = false;
				break;
			case ',':
				if ((inCurlies > 0) && !escaping) {
					sb.append('|');
				} else if (escaping) {
					sb.append("\\,");
				} else {
					sb.append(",");
				}
				break;
			default:
				escaping = false;
				sb.append(currentChar);
			}
		}
		pattern = Pattern.compile(sb.toString());
	}

	public boolean matches(final File file) {
		return matches(file.getName());
	}

	public boolean matches(final String filename) {
		Matcher m = pattern.matcher(filename);
		return m.matches();
	}

	@Override
	public String toString() {
		return pattern.toString();
	}
}
