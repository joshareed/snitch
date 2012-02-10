package com.refactr.snitch.rules;

import java.io.File;

import com.refactr.snitch.SnitchResult;

/**
 * Rules are responsible for checking files for violations.
 *
 * @author Josh Reed (josh@refactr.com)
 */
public interface Rule {

	/**
	 * Called after all files have been checked.
	 *
	 * @param results
	 *            the {@link SnitchResult} for this session.
	 */
	public void after(SnitchResult results);

	/**
	 * Called after each file to be checked.
	 *
	 * @param results
	 *            the {@link SnitchResult} for this session.
	 */
	public void afterFile(File file, SnitchResult results);

	/**
	 * Called before any files have been checked.
	 *
	 * @param results
	 *            the {@link SnitchResult} for this session.
	 */
	public boolean before(SnitchResult results);

	/**
	 * Called before each file to be checked.
	 *
	 * @param results
	 *            the {@link SnitchResult} for this session.
	 * @return return true if the file should be checked, false otherwise.
	 */
	public boolean beforeFile(File file, SnitchResult results);

	/**
	 * Called for each line in the file.
	 *
	 * @param file
	 *            the file.
	 * @param line
	 *            the line.
	 * @param num
	 *            the line number.
	 * @param results
	 *            the {@link SnitchResult} for this session.
	 */
	public void check(File file, String line, int num, SnitchResult results);

}
