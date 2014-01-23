package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.CNAMERecord;

import java.io.IOException;

public class CNAMERecordSerializer extends
		AbstractRecordSerializer<CNAMERecord> {

	public CNAMERecordSerializer() {
		super(CNAMERecord.class);
	}

	@Override
	protected void serializeRDataFields(final CNAMERecord cnameRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		if (cnameRecord.getTarget() != null) {
			jsonGenerator.writeStringField("target", cnameRecord.getTarget()
					.toString());
		}
	}
}
