package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;

public abstract class AbstractRule implements Rule {

	public AbstractRule() {
		super();
	}

	@Override
	public void after(final SnitchResult results) {
		// do nothing
	}

	@Override
	public void afterFile(final File file, final SnitchResult results) {
		// do nothing

	}

	@Override
	public boolean before(final SnitchResult results) {
		// default to enabled for this session
		return true;
	}

	@Override
	public boolean beforeFile(final File file, final SnitchResult results) {
		// default to enabled for this file
		return true;
	}

	@Override
	public void check(final File file, final String line, final int num, final SnitchResult results) {
		// do nothing
	}
}