package com.refactr.snitch;

import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLReport implements Report {

	@Override
	public void build(final SnitchResult results, final Writer writer) throws IOException {
		try {
			XMLStreamWriter xml = XMLOutputFactory.newFactory().createXMLStreamWriter(writer);
			xml.writeStartDocument();
			xml.writeStartElement("report");
			xml.writeStartElement("violations");
			for (Violation v : results.getViolations()) {
				xml.writeStartElement("violation");
				xml.writeAttribute("file", v.getFile().getName());
				xml.writeAttribute("path", v.getFile().getCanonicalPath());
				xml.writeAttribute("rule", v.getRule());
				xml.writeAttribute("line", Integer.toString(v.getLine()));
				xml.writeAttribute("message", v.getMessage());
				xml.writeEndElement();
			}
			xml.writeEndElement();
			xml.writeEndElement();
			xml.writeEndDocument();
			xml.flush();
			xml.close();
		} catch (XMLStreamException e) {
			throw new IOException("Error writing XML", e);
		} catch (FactoryConfigurationError e) {
			throw new IOException("Unable to get XML writer", e);
		}
	}
}
