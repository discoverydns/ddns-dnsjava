package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.ARecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.ARecord} class
 * @author Arnaud Dumont
 */
public class ARecordSerializer extends AbstractRecordSerializer<ARecord> {

	public ARecordSerializer() {
		super(ARecord.class);
	}

	@Override
	protected void serializeRDataFields(final ARecord aRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		if (aRecord.getAddress() != null) {
			jsonGenerator.writeStringField("address", aRecord.getAddress()
					.getHostAddress());
		}
	}
}
