package com.refactr.snitch.rules;

import java.io.File;
import java.util.List;

import com.refactr.snitch.Violation;

public interface Rule {

	public void check(File file, List<Violation> violations);
}
