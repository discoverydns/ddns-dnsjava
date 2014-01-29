package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.NSRecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.NSRecord} class
 * @author Arnaud Dumont
 */
public class NSRecordSerializer extends AbstractRecordSerializer<NSRecord> {

	public NSRecordSerializer() {
		super(NSRecord.class);
	}

	@Override
	protected void serializeRDataFields(final NSRecord nsRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		if (nsRecord.getTarget() != null) {
			jsonGenerator.writeStringField("target", nsRecord.getTarget()
					.toString());
		}
	}
}
