package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Name;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.NSRecord} class
 * @author Arnaud Dumont
 */
public class NSRecordDeserializer extends AbstractRecordDeserializer<NSRecord> {
	private static final long serialVersionUID = 448207168918952113L;

	public NSRecordDeserializer() {
		super(NSRecord.class);
	}

	@Override
	protected NSRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new NSRecord(name, dclass, ttl, getNodeNameValue(recordNode,
				"target"));
	}

	@Override
	protected String getTextualRecordType() {
		return "NS";
	}
}
