package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Name;
import org.xbill.DNS.SOARecord;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.SOARecord} class
 * @author Arnaud Dumont
 */
public class SOARecordDeserializer extends
		AbstractRecordDeserializer<SOARecord> {
	private static final long serialVersionUID = -568205639940212161L;

	public SOARecordDeserializer() {
		super(SOARecord.class);
	}

	@Override
	protected SOARecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new SOARecord(name, dclass, ttl, getNodeNameValue(recordNode,
				"host"), getNodeNameValue(recordNode, "admin"),
				getNodeLongValue(recordNode, "serial"), getNodeLongValue(
						recordNode, "refresh"), getNodeLongValue(recordNode,
						"retry"), getNodeLongValue(recordNode, "expire"),
				getNodeLongValue(recordNode, "minimum"));
	}

	@Override
	protected String getTextualRecordType() {
		return "SOA";
	}
}
