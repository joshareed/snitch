package com.refactr.snitch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class BlameService {

	protected Map<File, Map<Integer, String>> blameCache;

	public BlameService() {
		blameCache = new HashMap<File, Map<Integer, String>>();
	}

	public String getBlame(final File file, final int line, final SnitchResult results) {
		// build blame for the file
		if (!blameCache.containsKey(file)) {
			File project = results.getProject();
			File git = new File(project, ".git");
			if (git.exists()) {
				gitBlameForFile(file, git, project);
			}
		}

		// get the line map
		Map<Integer, String> lines = blameCache.get(file);
		if ((lines != null) && lines.containsKey(line)) {
			return lines.get(line);
		} else {
			return null;
		}
	}

	public String getBlame(final Violation v, final SnitchResult results) {
		return getBlame(v.getFile(), v.getLine(), results);
	}

	protected void gitBlameForFile(final File file, final File repo, final File working) {
		Map<Integer, String> lines = new HashMap<Integer, String>();
		blameCache.put(file, lines);

		BufferedReader in = null;
		try {
			Process p = Runtime.getRuntime().exec(
					new String[] { "git", "--git-dir=" + repo.getCanonicalPath(),
							"--work-tree=" + working.getCanonicalPath(), "--no-pager", "blame", "-c",
							file.getCanonicalPath() });

			// get the output
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// read the output from the git-blame
			String s;
			while ((s = in.readLine()) != null) {
				if (!"".equals(s.trim())) {
					String[] split = s.split("\t");
					if (split.length >= 4) {
						// author
						String author = split[1].trim();
						if (author.startsWith("(")) {
							author = author.substring(1).trim();
						}

						// line number
						String num = split[3].substring(0, split[3].indexOf(')')).trim();
						int line = Integer.parseInt(num);

						if ((author != null) && !"".equals(author) && (line > 0)) {
							lines.put(line, author);
						}
					}
				}
			}
			p.waitFor();
			p.destroy();
		} catch (Exception e) {
			// ignore
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
}
