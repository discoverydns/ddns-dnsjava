package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Name;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.ARecord} class
 * @author Arnaud Dumont
 */
public class ARecordDeserializer extends
		AbstractAddressRecordDeserializer<ARecord> {
	private static final long serialVersionUID = -330875628382721963L;

	public ARecordDeserializer() {
		super(ARecord.class);
	}

	@Override
	protected ARecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new ARecord(name, dclass, ttl, getNodeAddressValue(recordNode));
	}

	@Override
	protected String getTextualRecordType() {
		return "A";
	}
}
