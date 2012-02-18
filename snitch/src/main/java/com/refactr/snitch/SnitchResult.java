package com.refactr.snitch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SnitchResult {
	protected final Properties config;
	protected int files = 0;
	protected int lines = 0;
	protected final File project;
	protected long time = 0;
	protected final List<Violation> violations;
	protected final BlameService blameService;
	protected final Map<String, Integer> users;
	protected File lastFile = null;

	public SnitchResult(final File project) {
		this.project = project;
		violations = new ArrayList<Violation>();
		config = new Properties();
		blameService = new BlameService();
		users = new HashMap<String, Integer>();
		initConfig();
	}

	public void addViolation(final Violation v) {
		v.setBlame(getBlame(v.getFile(), v.getLine()));
		violations.add(v);
	}

	public void countLine(final File file, final int line) {
		// track stats about files, lines, and users
		if (file != lastFile) {
			files++;
			lastFile = file;
		}
		lines++;

		// get user blame
		String user = getBlame(file, line);
		if ((user != null) && !"".equals(user)) {
			if (users.containsKey(user)) {
				int count = users.get(user);
				users.put(user, count + 1);
			} else {
				users.put(user, 1);
			}
		}
	}

	public String getBlame(final File file, final int line) {
		return blameService.getBlame(file, line, this);
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

	public Map<String, Integer> getUsers() {
		return users;
	}

	public List<Violation> getViolations() {
		return violations;
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
