package com.refactr.snitch.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class BlameUtils {

	public static String getBlame(final Violation v, final SnitchResult results) {
		File project = results.getProject();
		File git = new File(project, ".git");
		if (git.exists()) {
			return gitBlame(v.getFile(), v.getLine(), git, project);
		}
		return null;
	}

	public static String gitBlame(final File file, final int line, final File repo, final File working) {
		BufferedReader in = null;
		BufferedReader err = null;
		try {
			Process p = Runtime.getRuntime().exec(
					new String[] { "git", "--git-dir=" + repo.getCanonicalPath(),
							"--work-tree=" + working.getCanonicalPath(), "blame", file.getCanonicalPath(),
							"-L" + line + "," + line, "-c" });

			// get the output
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			err = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read any errors from the attempted command
			String s;
			while ((s = err.readLine()) != null) {
				// ignore
			}

			// read the output from the git-blame
			String blame = null;
			while ((s = in.readLine()) != null) {
				if (!"".equals(s.trim())) {
					String[] split = s.split("\t");
					if (split.length >= 2) {
						blame = split[1].trim();
						if (blame.startsWith("(")) {
							blame = blame.substring(1).trim();
						}
					}
				}
			}
			p.destroy();
			if ((blame == null) || "".equals(blame)) {
				return null;
			} else {
				return blame;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// ignore
				}
			}
			if (err != null) {
				try {
					err.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return null;
	}

	private BlameUtils() {
		// not to be instantiated
	}
}
