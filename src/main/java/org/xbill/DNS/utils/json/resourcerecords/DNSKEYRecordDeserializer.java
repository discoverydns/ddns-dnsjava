package org.xbill.DNS.utils.json.resourcerecords;

import org.xbill.DNS.DNSKEYRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.DNSKEYRecord} class
 * 
 * @author Chris Wright
 */
public class DNSKEYRecordDeserializer extends
		AbstractRecordDeserializer<DNSKEYRecord> {

	private static final long serialVersionUID = -624474520652580649L;

	public DNSKEYRecordDeserializer() {
		super(DNSKEYRecord.class);
	}

	@Override
	protected DNSKEYRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
        try {
            return new DNSKEYRecord(name, dclass, ttl, getNodeIntegerValue(
                    recordNode, "flags"), getNodeIntegerValue(recordNode,
                    "protocol"), getNodeIntegerValue(recordNode, "algorithm"),
                    getNodeStringValue(recordNode, "key"));
        } catch (final IOException e) {
            throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.unexpectedMappingError,
                    e, getTextualBeanType(), e.getMessage());
        }
    }

	@Override
	protected String getTextualRecordType() {
		return "DNSKEY";
	}
}
