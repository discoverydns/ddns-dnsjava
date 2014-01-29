package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.CERTRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.io.IOException;

public class CERTRecordDeserializer extends
		AbstractRecordDeserializer<CERTRecord> {
	private static final long serialVersionUID = 1976274354370729318L;

	public CERTRecordDeserializer() {
		super(CERTRecord.class);
	}

	@Override
	protected CERTRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		try {
			return new CERTRecord(name, dclass, ttl, getNodeIntegerValue(
					recordNode, "certType"), getNodeIntegerValue(recordNode,
					"keyTag"), getNodeIntegerValue(recordNode, "algorithm"),
					getNodeStringValue(recordNode, "cert"));
		} catch (final IOException e) {
			throw new JsonDeserializationException(
					JsonDeserializationExceptionCode.unexpectedMappingError,
					e, getTextualBeanType(), e.getMessage());
		}
	}

	@Override
	protected String getTextualRecordType() {
		return "CERT";
	}
}
