package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;
import org.xbill.DNS.Name;
import org.xbill.DNS.SPFRecord;
import org.xbill.DNS.Type;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.io.IOException;

public class SPFRecordDeserializer extends
		AbstractRecordDeserializer<SPFRecord> {
	private static final long serialVersionUID = 988481497237099680L;

	private static final String STRINGS_FIELD_NAME = "strings";

	public SPFRecordDeserializer() {
		super(SPFRecord.class);
	}

	@Override
	protected SPFRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		try {
            String strings = getNodeStringValue(recordNode, STRINGS_FIELD_NAME);
            if (!CharMatcher.ASCII.matchesAllOf(strings)) {
                throw new JsonDeserializationException(
                        JsonDeserializationExceptionCode.invalidFieldValue,
                        "strings", getTextualBeanType(), "Non-ASCII character found");
            }
            return (SPFRecord) SPFRecord.fromString(name, Type.SPF, dclass,
					ttl, strings, Name.root);
		} catch (final IOException e) {
			throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.unexpectedMappingError,
					e, getTextualBeanType(), e.getMessage());
		}
	}

	@Override
	protected String getTextualRecordType() {
		return "SPF";
	}
}
