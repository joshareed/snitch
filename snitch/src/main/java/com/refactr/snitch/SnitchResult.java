package com.refactr.snitch;

import java.util.ArrayList;
import java.util.List;

public class SnitchResult {
	protected final List<Violation> violations;

	public SnitchResult() {
		violations = new ArrayList<Violation>();
	}

	public void addViolation(final Violation v) {
		violations.add(v);
	}

	public List<Violation> getViolations() {
		return violations;
	}
}
