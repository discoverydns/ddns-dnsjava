package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Name;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.MXRecord} class
 * @author Arnaud Dumont
 */
public class MXRecordDeserializer extends AbstractRecordDeserializer<MXRecord> {
	private static final long serialVersionUID = -9157137850645403440L;

	public MXRecordDeserializer() {
		super(MXRecord.class);
	}

	@Override
	protected MXRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new MXRecord(name, dclass, ttl, getNodeIntegerValue(recordNode,
				"priority"), getNodeNameValue(recordNode, "target"));
	}

	@Override
	protected String getTextualRecordType() {
		return "MX";
	}
}
