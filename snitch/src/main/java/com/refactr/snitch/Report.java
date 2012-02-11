package com.refactr.snitch;

import java.io.IOException;
import java.io.Writer;

public interface Report {

	void build(SnitchResult result, Writer writer) throws IOException;
}
