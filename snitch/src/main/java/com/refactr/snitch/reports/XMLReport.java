package com.refactr.snitch.reports;

import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class XMLReport implements Report {
	protected BlameService blameService;

	public XMLReport() {
		blameService = new BlameService();
	}

	@Override
	public void build(final SnitchResult results, final Writer writer) throws IOException {
		XMLStreamWriter xml = null;
		try {
			xml = XMLOutputFactory.newFactory().createXMLStreamWriter(writer);
			xml.writeStartDocument();
			xml.writeStartElement("report");
			xml.writeStartElement("project");
			xml.writeAttribute("name", results.getProject().getName());
			xml.writeAttribute("path", results.getProject().getCanonicalPath());
			xml.writeEndElement();
			xml.writeStartElement("violations");
			for (Violation v : results.getViolations()) {
				xml.writeStartElement("violation");
				String blame = blameService.getBlame(v, results);
				if ((blame != null) && !"".equals(blame.trim())) {
					xml.writeAttribute("blame", blame.trim());
				}
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
