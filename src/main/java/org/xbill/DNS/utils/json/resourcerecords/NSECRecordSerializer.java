package org.xbill.DNS.utils.json.resourcerecords;

import java.io.IOException;

import org.xbill.DNS.NSECRecord;
import org.xbill.DNS.Type;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson serializer for the {@link org.xbill.DNS.NSECRecord} class
 * 
 * @author Chris Wright
 */
public class NSECRecordSerializer extends AbstractRecordSerializer<NSECRecord> {

	public NSECRecordSerializer() {
		super(NSECRecord.class);
	}

	@Override
	protected void serializeRDataFields(final NSECRecord nsecRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeStringField("nextDomain", nsecRecord.getNext()
				.toString());
		StringBuilder typesStringBuilder = new StringBuilder();
		for (int type : nsecRecord.getTypes()) {
			if (typesStringBuilder.length() > 0) {
				typesStringBuilder.append(" ");
			}
			typesStringBuilder.append(Type.string(type));
		}
		jsonGenerator.writeStringField("types", typesStringBuilder.toString());

	}
}
