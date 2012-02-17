package com.refactr.snitch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTypes {
	public static final String[] CSS = new String[] { ".css" };
	public static final String[] JS = new String[] { ".js" };
	public static final String[] HTML = new String[] { ".html" };
	public static final String[] GSP = new String[] { ".gsp" };
	public static final String[] GROOVY = new String[] { ".groovy" };
	public static final String[] JAVA = new String[] { ".java" };
	public static final String[] MARKUP = join(HTML, GSP);
	public static final String[] CODE = join(JAVA, GROOVY);
	public static final String[] GRAILS = join(GROOVY, GSP, HTML, JS, CSS);

	public static String[] join(final String[]... groups) {
		List<String> joined = new ArrayList<String>();
		for (String[] g : groups) {
			joined.addAll(Arrays.asList(g));
		}
		return joined.toArray(new String[0]);
	}

	private FileTypes() {
		// not to be instantiated
	}
}
