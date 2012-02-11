package com.refactr.snitch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GlobTest {

	@Test
	public void testMatchesAlternation() {
		Glob glob = new Glob("*.{html,js,css}");
		assertFalse(glob.matches("bin"));
		assertTrue(glob.matches("index.html"));
		assertTrue(glob.matches("application.js"));
		assertTrue(glob.matches("style.css"));
		assertFalse(glob.matches("main.gsp"));
	}

	@Test
	public void testMatchesComplex() {
		Glob glob = new Glob("{*.{html,js,css},build,bin}");
		assertTrue(glob.matches("bin"));
		assertTrue(glob.matches("index.html"));
		assertTrue(glob.matches("application.js"));
		assertTrue(glob.matches("style.css"));
		assertFalse(glob.matches("main.gsp"));
	}

	@Test
	public void testMatchesEmpty() {
		Glob glob = new Glob("");
		assertFalse(glob.matches("bin"));
		assertFalse(glob.matches("build"));
		assertFalse(glob.matches("builddir"));
	}

	@Test
	public void testMatchesExact() {
		Glob glob = new Glob("build");
		assertFalse(glob.matches("bin"));
		assertTrue(glob.matches("build"));
		assertFalse(glob.matches("builddir"));
	}

	@Test
	public void testMatchesQuestion() {
		Glob glob = new Glob("?uild*");
		assertFalse(glob.matches("bin"));
		assertTrue(glob.matches("build"));
		assertTrue(glob.matches("cuilddir"));
		assertFalse(glob.matches("style.css"));
	}

	@Test
	public void testMatchesTrailingWildcard() {
		Glob glob = new Glob("build*");
		assertFalse(glob.matches("bin"));
		assertTrue(glob.matches("build"));
		assertTrue(glob.matches("builddir"));
		assertFalse(glob.matches("test.build"));
	}

	@Test
	public void testMatchesWildcard() {
		Glob glob = new Glob("*.html");
		assertFalse(glob.matches("bin"));
		assertFalse(glob.matches("build"));
		assertTrue(glob.matches("index.html"));
		assertFalse(glob.matches("style.css"));
	}
}
