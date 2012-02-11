package com.refactr.snitch.reports;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.refactr.snitch.SnitchResult;
import com.refactr.snitch.Violation;

public class XMLReport implements Report {

	@Override
	public void build(final SnitchResult results, final Writer writer) throws IOException {
		try {
			StringWriter out = new StringWriter();
			XMLStreamWriter xml = XMLOutputFactory.newFactory().createXMLStreamWriter(out);
			xml.writeStartDocument();
			xml.writeStartElement("report");
			xml.writeStartElement("project");
			xml.writeAttribute("name", results.getProject().getName());
			xml.writeAttribute("path", results.getProject().getCanonicalPath());
			xml.writeEndElement();
			xml.writeStartElement("violations");
			for (Violation v : results.getViolations()) {
				xml.writeStartElement("violation");
				String blame = BlameUtils.getBlame(v, results);
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
			xml.close();

			// format the report
			Source source = new StreamSource(new StringReader(out.toString()));
			Result result = new StreamResult(writer);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
		} catch (XMLStreamException e) {
			throw new IOException("Error writing XML", e);
		} catch (FactoryConfigurationError e) {
			throw new IOException("Unable to get XML writer", e);
		} catch (TransformerConfigurationException e) {
			throw new IOException("Unable to get XML writer", e);
		} catch (TransformerFactoryConfigurationError e) {
			throw new IOException("Unable to get XML writer", e);
		} catch (TransformerException e) {
			throw new IOException("Unable to get XML writer", e);
		}
	}
}
