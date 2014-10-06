package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.NSECRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.Type;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.NSECRecord} class.
 * @author Behzad Mozaffari
 */
public class NSECRecordDeserializer extends
		AbstractRecordDeserializer<NSECRecord> {

	public NSECRecordDeserializer() {
		super(NSECRecord.class);
	}

	@Override
	protected NSECRecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {

        int[] types;
        String typesString = getNodeStringValue(recordNode, "types");
        if (typesString == null || typesString.trim().length() == 0) {
            types = new int[0];
        } else {
            String[] typeStrings = typesString.trim().split(" ");
            types = new int[typeStrings.length];
            for (int i = 0; i < typeStrings.length; i++) {
                int typeIntValue = Type.value(typeStrings[i]);
                if (typeIntValue < 0) {
                    throw new JsonDeserializationException(
                            JsonDeserializationException.JsonDeserializationExceptionCode.invalidFieldValue,
                            "types", getTextualBeanType());
                }
                types[i] = typeIntValue;
            }
        }
        return new NSECRecord(name, dclass, ttl, getNodeNameValue(recordNode, "nextDomain"),
                types);
	}

    @Override
	protected String getTextualRecordType() {
		return "NSEC";
	}
}
