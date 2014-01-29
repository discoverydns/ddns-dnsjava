package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Name;
import org.xbill.DNS.SSHFPRecord;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.io.IOException;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.SSHFPRecord} class
 * @author Arnaud Dumont
 */
public class SSHFPRecordDeserializer extends
		AbstractRecordDeserializer<SSHFPRecord> {
	private static final long serialVersionUID = -3992024906736725094L;

	public SSHFPRecordDeserializer() {
		super(SSHFPRecord.class);
	}

	@Override
	protected SSHFPRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		try {
			return new SSHFPRecord(name, dclass, ttl, getNodeIntegerValue(
					recordNode, "algorithm"), getNodeIntegerValue(recordNode,
					"digestType"),
					getNodeStringValue(recordNode, "fingerprint"));
		} catch (final IOException e) {
			throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.unexpectedMappingError,
					e, getTextualBeanType(), e.getMessage());
		}
	}

	@Override
	protected String getTextualRecordType() {
		return "SSHFP";
	}
}
