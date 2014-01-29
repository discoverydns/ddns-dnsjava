package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.DSRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.io.IOException;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.DSRecord} class
 * @author Arnaud Dumont
 */
public class DSRecordDeserializer extends AbstractRecordDeserializer<DSRecord> {
	private static final long serialVersionUID = 9205297205421105521L;

	public DSRecordDeserializer() {
		super(DSRecord.class);
	}

	@Override
	protected DSRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		try {
			return new DSRecord(name, dclass, ttl, getNodeIntegerValue(
					recordNode, "footprint"), getNodeIntegerValue(recordNode,
					"algorithm"), getNodeIntegerValue(recordNode, "digestId"),
					getNodeStringValue(recordNode, "digest"));
		} catch (final IOException e) {
			throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.unexpectedMappingError,
					e, getTextualBeanType(), e.getMessage());
		}
	}

	@Override
	protected String getTextualRecordType() {
		return "DS";
	}
}
