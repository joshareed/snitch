package com.refactr.snitch.reports;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class XMLReport implements Report {

	@Override
	public void build(final SnitchResult results, final Writer writer) throws IOException {
		int strip = results.getProject().getCanonicalPath().length() + 1;
		XMLStreamWriter xml = null;
		try {
			xml = XMLOutputFactory.newFactory().createXMLStreamWriter(writer);
			xml.writeStartDocument();
			xml.writeStartElement("report");
			xml.writeEmptyElement("project");
			xml.writeAttribute("name", results.getProject().getName());
			xml.writeAttribute("path", results.getProject().getCanonicalPath());

			xml.writeEmptyElement("summary");
			xml.writeAttribute("files", "" + results.getFiles());
			xml.writeAttribute("lines", "" + results.getLines());
			xml.writeAttribute("violations", "" + results.getViolations().size());
			xml.writeAttribute("time", "" + results.getTime());

			List<Violation> violations = results.getViolations();
			Collections.sort(violations);

			xml.writeStartElement("violations");
			File current = null;
			for (Violation v : violations) {
				if (current != v.getFile()) {
					if (current != null) {
						xml.writeEndElement();
					}
					current = v.getFile();
					xml.writeStartElement("file");
					xml.writeAttribute("name", v.getFile().getName());
					xml.writeAttribute("path", v.getFile().getCanonicalPath().substring(strip));
				}
				xml.writeEmptyElement("violation");
				String blame = v.getBlame();
				if ((blame != null) && !"".equals(blame.trim())) {
					xml.writeAttribute("blame", blame.trim());
				}
				xml.writeAttribute("rule", v.getRule());
				xml.writeAttribute("line", Integer.toString(v.getLine()));
				xml.writeAttribute("message", v.getMessage());
			}
			xml.writeEndDocument();
			xml.flush();
		} catch (XMLStreamException e) {
			throw new IOException("Error writing XML", e);
		} catch (FactoryConfigurationError e) {
			throw new IOException("Unable to get XML writer", e);
		} catch (TransformerFactoryConfigurationError e) {
			throw new IOException("Unable to get XML writer", e);
		} finally {
			if (xml != null) {
				try {
					xml.close();
				} catch (XMLStreamException e) {
					// ignore
				}
			}
			writer.close();
		}
	}
}
