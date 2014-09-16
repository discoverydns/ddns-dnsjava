package org.xbill.DNS.utils.json.resourcerecords;

import java.util.ArrayList;
import java.util.List;

import org.xbill.DNS.Name;
import org.xbill.DNS.SPFRecord;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.SPFRecord} class
 * @author Arnaud Dumont
 */
public class SPFRecordDeserializer extends
		AbstractRecordDeserializer<SPFRecord> {
    private static final String STRINGS_FIELD_NAME = "strings";
	private static final long serialVersionUID = 988481497237099680L;

    public SPFRecordDeserializer() {
        super(SPFRecord.class);
    }

    @Override
    protected SPFRecord createRecord(final Name name, final int dclass,
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
                return new SPFRecord(name, dclass, ttl, strings);
            } else {
                String strings = fieldNode.textValue();
                checkStringIsASCII(strings);
                return new SPFRecord(name, dclass, ttl, strings);
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
        return "SPF";
    }
}
