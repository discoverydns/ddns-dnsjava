package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Name;
import org.xbill.DNS.TLSARecord;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.io.IOException;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.TLSARecord} class
 * @author Arnaud Dumont
 */
public class TLSARecordDeserializer extends
		AbstractRecordDeserializer<TLSARecord> {
	private static final long serialVersionUID = -9043318764404295577L;

	public TLSARecordDeserializer() {
		super(TLSARecord.class);
	}

	@Override
	protected TLSARecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		try {
			return new TLSARecord(name, dclass, ttl, getNodeIntegerValue(
					recordNode, "certificateUsage"), getNodeIntegerValue(
					recordNode, "selector"), getNodeIntegerValue(recordNode,
					"matchingType"), getNodeStringValue(recordNode,
					"certificateAssociationData"));
		} catch (final IOException e) {
			throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.unexpectedMappingError,
					e, getTextualBeanType(), e.getMessage());
		}
	}

	@Override
	protected String getTextualRecordType() {
		return "TLSA";
	}
}
