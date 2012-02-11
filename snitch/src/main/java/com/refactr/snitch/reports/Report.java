package com.refactr.snitch.reports;

import java.io.IOException;
import java.io.Writer;

import com.refactr.snitch.SnitchResult;

public interface Report {

	void build(SnitchResult results, Writer writer) throws IOException;
}
