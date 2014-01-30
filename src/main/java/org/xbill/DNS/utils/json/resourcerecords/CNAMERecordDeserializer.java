package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Name;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.CNAMERecord} class
 * @author Arnaud Dumont
 */
public class CNAMERecordDeserializer extends
		AbstractRecordDeserializer<CNAMERecord> {
	private static final long serialVersionUID = -6079934308228017278L;

	public CNAMERecordDeserializer() {
		super(CNAMERecord.class);
	}

	@Override
	protected CNAMERecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new CNAMERecord(name, dclass, ttl, getNodeNameValue(recordNode,
				"target"));
	}

	@Override
	protected String getTextualRecordType() {
		return "CNAME";
	}
}
