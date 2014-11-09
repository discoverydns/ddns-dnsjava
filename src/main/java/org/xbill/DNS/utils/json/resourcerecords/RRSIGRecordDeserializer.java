package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.utils.base64;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.RRSIGRecord} class.
 * @author Behzad Mozaffari
 */
public class RRSIGRecordDeserializer extends
		AbstractRecordDeserializer<RRSIGRecord> {

	public RRSIGRecordDeserializer() {
		super(RRSIGRecord.class);
	}

	@Override
	protected RRSIGRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {

        return new RRSIGRecord(name, dclass, ttl, getNodeIntegerValue(recordNode, "typeCovered"),
                getNodeIntegerValue(recordNode, "algorithm"), getNodeLongValue(recordNode, "originalTTL"),
                getNodeDateValue(recordNode, "expireTime"), getNodeDateValue(recordNode, "inceptionTime"),
                getNodeIntegerValue(recordNode, "keyTag"),
                getNodeNameValue(recordNode, "signerName"),
                base64.fromString(getNodeStringValue(recordNode, "signature")));
	}

    @Override
	protected String getTextualRecordType() {
		return "RRSIG";
	}
}
