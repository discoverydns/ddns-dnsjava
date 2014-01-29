package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.PTRRecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.PTRRecord} class
 * @author Arnaud Dumont
 */
public class PTRRecordSerializer extends AbstractRecordSerializer<PTRRecord> {

	public PTRRecordSerializer() {
		super(PTRRecord.class);
	}

	@Override
	protected void serializeRDataFields(final PTRRecord ptrRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		if (ptrRecord.getTarget() != null) {
			jsonGenerator.writeStringField("target", ptrRecord.getTarget()
					.toString());
		}
	}
}
