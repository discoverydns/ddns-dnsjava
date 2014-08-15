package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.TXTRecord;

import java.io.IOException;
import java.util.List;

/**
 * Jackson serializer for the {@link org.xbill.DNS.TXTRecord} class
 * @author Arnaud Dumont
 */
public class TXTRecordSerializer extends AbstractRecordSerializer<TXTRecord> {

	public TXTRecordSerializer() {
		super(TXTRecord.class);
	}

	@Override
	protected void serializeRDataFields(final TXTRecord txtRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
        List strings = txtRecord.getStrings();
        if (strings.size() == 1) {
            jsonGenerator.writeStringField("strings", (String) strings.iterator().next());
        } else {
            jsonGenerator.writeArrayFieldStart("strings");
            for (Object string : strings) {
                jsonGenerator.writeString((String) string);
            }
            jsonGenerator.writeEndArray();
        }
	}
}
