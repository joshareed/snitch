package com.refactr.snitch.rules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.refactr.snitch.Violation;

public abstract class LineRule implements Rule {

	public LineRule() {
		super();
	}

	@Override
	public void check(final File file, final List<Violation> violations) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			int i = 1;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					String violation = checkLine(line);
					if (violation != null) {
						violations.add(new Violation(file, violation, i));
					}
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected abstract String checkLine(String line);
}