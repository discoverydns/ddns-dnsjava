package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;
import org.xbill.DNS.Name;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.util.ArrayList;
import java.util.List;

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
            JsonNode fieldNode = findFieldNode(recordNode, STRINGS_FIELD_NAME);

            if (fieldNode.isArray()) {
                List<String> strings = new ArrayList<String>(fieldNode.size());
                for (JsonNode jsonNode : fieldNode) {
                    String string = jsonNode.textValue();
                    checkStringIsASCII(string);
                    strings.add(string);
                }
                return new TXTRecord(name, dclass, ttl, strings);
            } else {
                String strings = fieldNode.textValue();
                checkStringIsASCII(strings);
                return new TXTRecord(name, dclass, ttl, strings);
            }
        } catch (final JsonDeserializationException e) {
            throw e;
		} catch (final Exception e) {
			throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.unexpectedMappingError,
					e, getTextualBeanType(), e.getMessage());
		}
	}

    private void checkStringIsASCII(String string) {
        if (!CharMatcher.ASCII.matchesAllOf(string)) {
            throw new JsonDeserializationException(
                    JsonDeserializationExceptionCode.invalidFieldValue,
                    "strings", getTextualBeanType(), "Non-ASCII character found");
        }
    }

    @Override
	protected String getTextualRecordType() {
		return "TXT";
	}
}
