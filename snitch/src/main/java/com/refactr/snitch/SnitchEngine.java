package com.refactr.snitch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.refactr.snitch.rules.InlineScriptBlockRule;
import com.refactr.snitch.rules.InlineStyleBlockRule;
import com.refactr.snitch.rules.Rule;
import com.refactr.snitch.rules.TabsForIndentationRule;
import com.refactr.snitch.rules.TrailingWhitespaceRule;

public class SnitchEngine {

	public static void main(final String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: java -jar snitch-all-<version>.jar <dir>");
			System.exit(1);
		} else {
			SnitchEngine engine = new SnitchEngine();
			SnitchResult results = engine.check(new File(args[0]));
			// TODO output in various formats
			for (Violation v : results.getViolations()) {
				System.out.println(v);
			}
		}
	}

	protected List<Rule> rules = null;

	public SnitchEngine() {
		this.rules = discoverRules();
	}

	public SnitchEngine(final List<Rule> rules) {
		this.rules = rules;
	}

	public SnitchResult check(final File project) {
		long start = System.currentTimeMillis();

		// discover rules if not specified
		if (rules == null) {
			rules = discoverRules();
		}

		SnitchResult results = new SnitchResult();

		// find active rules for this session
		List<Rule> activeRules = new ArrayList<Rule>();
		for (Rule r : rules) {
			if (r.before(results)) {
				activeRules.add(r);
			}
		}

		// check each file in the project
		check(project, activeRules, results);

		System.out.println((System.currentTimeMillis() - start) + "ms");
		return results;
	}

	protected void check(final File file, final List<Rule> rules, final SnitchResult results) {
		// skip if excluded
		if (isExcluded(file)) {
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
		} else {
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
		rules.add(new TabsForIndentationRule());
		rules.add(new TrailingWhitespaceRule());
		rules.add(new InlineStyleBlockRule());
		rules.add(new InlineScriptBlockRule());
		return rules;
	}

	protected boolean isExcluded(final File file) {
		// TODO make this configurable, perhaps via a .snitch file
		return ("bin".equals(file.getName()) || "build".equals(file.getName()) || "target".equals(file.getName())
				|| ".gradle".equals(file.getName()) || ".git".equals(file.getName()));
	}
}
