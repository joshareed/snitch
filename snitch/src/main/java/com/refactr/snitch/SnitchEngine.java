package com.refactr.snitch;

import java.io.File;
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
			List<Violation> violations = engine.check(new File(args[0]));
			// TODO output in various formats
			for (Violation v : violations) {
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

	public List<Violation> check(final File project) {
		long start = System.currentTimeMillis();

		// discover rules if not specified
		if (rules == null) {
			rules = discoverRules();
		}

		// check each file in the project
		List<Violation> violations = new ArrayList<Violation>();
		check(project, rules, violations);

		System.out.println((System.currentTimeMillis() - start) + "ms");
		return violations;
	}

	protected void check(final File file, final List<Rule> rules, final List<Violation> violations) {
		// skip if excluded
		if (isExcluded(file)) {
			return;
		}

		if (file.isDirectory()) {
			// recurse if directory
			File[] files = file.listFiles();
			if ((files != null) && (files.length > 0)) {
				for (File f : files) {
					check(f, rules, violations);
				}
			}
		} else {
			// check the file with all rules
			for (Rule rule : rules) {
				rule.check(file, violations);
			}
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
