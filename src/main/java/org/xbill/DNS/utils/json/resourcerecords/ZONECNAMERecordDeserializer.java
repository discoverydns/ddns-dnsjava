package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Name;
import org.xbill.DNS.ZONECNAMERecord;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.ZONECNAMERecord} class
 * @author Arnaud Dumont
 */
public class ZONECNAMERecordDeserializer extends
		AbstractRecordDeserializer<ZONECNAMERecord> {
    private static final long serialVersionUID = 5273240620605901385L;

    public ZONECNAMERecordDeserializer() {
		super(ZONECNAMERecord.class);
	}

	@Override
	protected ZONECNAMERecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new ZONECNAMERecord(name, dclass, ttl, getNodeNameValue(recordNode,
				"target"));
	}

	@Override
	protected String getTextualRecordType() {
		return "ZONECNAME";
	}
}
