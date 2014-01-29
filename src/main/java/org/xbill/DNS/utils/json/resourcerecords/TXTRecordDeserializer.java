package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;
import org.xbill.DNS.Name;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.Type;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.io.IOException;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.TXTRecord} class
 * @author Arnaud Dumont
 */
public class TXTRecordDeserializer extends
		AbstractRecordDeserializer<TXTRecord> {
	private static final String STRINGS_FIELD_NAME = "strings";
	private static final long serialVersionUID = -6018035758494572491L;

	public TXTRecordDeserializer() {
		super(TXTRecord.class);
	}

	@Override
	protected TXTRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		try {
            String strings = getNodeStringValue(recordNode, STRINGS_FIELD_NAME);
            if (!CharMatcher.ASCII.matchesAllOf(strings)) {
                throw new JsonDeserializationException(
                        JsonDeserializationExceptionCode.invalidFieldValue,
                        "strings", getTextualBeanType(), "Non-ASCII character found");
            }
            return (TXTRecord) TXTRecord.fromString(name, Type.TXT, dclass,
					ttl, strings, Name.root);
		} catch (final IOException e) {
			throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.unexpectedMappingError,
					e, getTextualBeanType(), e.getMessage());
		}
	}

	@Override
	protected String getTextualRecordType() {
		return "TXT";
	}
}
