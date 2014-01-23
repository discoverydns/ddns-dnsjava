package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Name;
import org.xbill.DNS.PTRRecord;

public class PTRRecordDeserializer extends
		AbstractRecordDeserializer<PTRRecord> {
	private static final long serialVersionUID = -7237324659966254820L;

	public PTRRecordDeserializer() {
		super(PTRRecord.class);
	}

	@Override
	protected PTRRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new PTRRecord(name, dclass, ttl, getNodeNameValue(recordNode,
				"target"));
	}

	@Override
	protected String getTextualRecordType() {
		return "PTR";
	}
}
