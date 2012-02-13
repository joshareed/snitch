package com.refactr.snitch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SnitchResult {
	protected final Properties config;
	protected int files = 0;
	protected int lines = 0;
	protected final File project;
	protected long time = 0;
	protected final List<Violation> violations;

	public SnitchResult(final File project) {
		this.project = project;
		violations = new ArrayList<Violation>();
		config = new Properties();
		initConfig();
	}

	public void addViolation(final Violation v) {
		violations.add(v);
	}

	public Properties getConfig() {
		return config;
	}

	public int getFiles() {
		return files;
	}

	public int getLines() {
		return lines;
	}

	public File getProject() {
		return project;
	}

	public long getTime() {
		return time;
	}

	public List<Violation> getViolations() {
		return violations;
	}

	public void incrementFiles() {
		this.files++;
	}

	public void incrementLines() {
		this.lines++;
	}

	protected Properties initConfig() {
		// check relative to the project
		File file = new File(project, ".snitch");
		if (file.exists() && file.isFile()) {
			try {
				config.load(new FileReader(file));
				return config;
			} catch (FileNotFoundException e) {
				// will never happen as we check
			} catch (IOException e) {
				System.err.println("Unable to load settings from " + file.getAbsolutePath());
			}
		}

		// check in user home
		File home = new File(System.getProperty("user.home"));
		file = new File(home, ".snitch");
		if (file.exists() && file.isFile()) {
			try {
				config.load(new FileReader(file));
				return config;
			} catch (FileNotFoundException e) {
				// will never happen as we check
			} catch (IOException e) {
				System.err.println("Unable to load settings from " + file.getAbsolutePath());
			}
		}
		return config;
	}

	public void setFiles(final int files) {
		this.files = files;
	}

	public void setLines(final int lines) {
		this.lines = lines;
	}

	public void setTime(final long time) {
		this.time = time;
	}
}
