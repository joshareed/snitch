package com.refactr.snitch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.refactr.snitch.reports.XMLReport;
import com.refactr.snitch.rules.EmbeddedScriptBlockRule;
import com.refactr.snitch.rules.EmbeddedStyleBlockRule;
import com.refactr.snitch.rules.Rule;
import com.refactr.snitch.rules.SpacesForIndentationRule;
import com.refactr.snitch.rules.TrailingWhitespaceRule;

public class SnitchEngine {
	public static void main(final String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage: java -jar snitch-all-<version>.jar <dir>");
			System.exit(1);
		} else {
			File project = new File(args[0]);
			long start = System.currentTimeMillis();
			SnitchEngine engine = new SnitchEngine();
			SnitchResult results = engine.check(project);
			long end = System.currentTimeMillis();
			results.setTime(end - start);
			System.out.println(String.format("[%s] %d files :: %d lines :: %d violations :: %d ms", project.getName(),
					results.getFiles(), results.getLines(), results.getViolations().size(), (end - start)));

			System.out.print("Building report for " + results.getProject().getName() + "....");
			XMLReport xml = new XMLReport();
			xml.build(results, new FileWriter(new File(results.getProject(), "snitch.xml")));
			System.out.println("Done!");
		}
	}

	protected Glob excludes = null;
	protected Glob includes = null;
	protected List<Rule> rules = null;

	public SnitchEngine() {
		this.rules = discoverRules();
	}

	public SnitchEngine(final List<Rule> rules) {
		this.rules = rules;
	}

	public SnitchResult check(final File project) {
		// discover rules if not specified
		if (rules == null) {
			rules = discoverRules();
		}

		// initialize our results
		SnitchResult results = new SnitchResult(project);
		parseConfig(results.getConfig());

		// find active rules for this session
		List<Rule> activeRules = new ArrayList<Rule>();
		for (Rule r : rules) {
			if (r.before(results)) {
				activeRules.add(r);
			}
		}

		// check each file in the project
		check(project, activeRules, results);

		return results;
	}

	protected void check(final File file, final List<Rule> rules, final SnitchResult results) {
		if (!shouldCheck(file)) {
			return;
		}

		if (file.isDirectory()) {
			// recurse if directory
			File[] files = file.listFiles();
			if ((files != null) && (files.length > 0)) {
				for (File f : files) {
					check(f, rules, results);
				}
			}
		} else if (file.isFile()) {
			checkFile(file, rules, results);
		}
	}

	protected void checkFile(final File file, final List<Rule> rules, final SnitchResult results) {
		List<Rule> active = new ArrayList<Rule>();

		// find active rules for this file
		for (Rule r : rules) {
			if (r.beforeFile(file, results)) {
				active.add(r);
			}
		}

		// parse and check the file by line
		if (!active.isEmpty()) {
			results.incrementFiles();
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String line = null;
				int i = 1;
				while ((line = br.readLine()) != null) {
					for (Rule r : active) {
						r.check(file, line, i, results);
					}
					i++;
					results.incrementLines();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// notify active rules after this file has been checked
		for (Rule r : active) {
			r.afterFile(file, results);
		}
	}

	protected List<Rule> discoverRules() {
		// TODO allow rules to be configured or discovered, perhaps via a .snitch file
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(new SpacesForIndentationRule());
		rules.add(new TrailingWhitespaceRule());
		rules.add(new EmbeddedStyleBlockRule());
		rules.add(new EmbeddedScriptBlockRule());
		return rules;
	}

	protected void parseConfig(final Properties config) {
		if (config.containsKey("exclude")) {
			String glob = config.getProperty("exclude");
			if ((glob != null) && !"".equals(glob.trim())) {
				excludes = new Glob(glob);
			}
		}
		if (config.containsKey("include")) {
			String glob = config.getProperty("include");
			if ((glob != null) && !"".equals(glob.trim())) {
				includes = new Glob(glob);
			}
		}
	}

	protected boolean shouldCheck(final File file) {
		if (file.isDirectory()) {
			return ((excludes == null) || !excludes.matches(file));
		} else {
			// check if explicitly included
			if ((excludes != null) && excludes.matches(file)) {
				return false;
			}
			if (includes != null) {
				return includes.matches(file);
			}
			return true;
		}
	}
}
