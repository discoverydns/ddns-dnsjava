package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.SPFRecord;

import java.io.IOException;
import java.util.List;

/**
 * Jackson serializer for the {@link org.xbill.DNS.SPFRecord} class
 * @author Arnaud Dumont
 */
public class SPFRecordSerializer extends AbstractRecordSerializer<SPFRecord> {

	public SPFRecordSerializer() {
		super(SPFRecord.class);
	}

	@Override
	protected void serializeRDataFields(final SPFRecord spfRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
        @SuppressWarnings("rawtypes")
        List strings = spfRecord.getStrings();
        if (strings.size() == 1) {
            jsonGenerator.writeStringField("strings", (String) strings
                    .iterator().next());
        } else {
            jsonGenerator.writeArrayFieldStart("strings");
            for (Object string : strings) {
                jsonGenerator.writeString((String) string);
            }
            jsonGenerator.writeEndArray();
        }
	}
}
