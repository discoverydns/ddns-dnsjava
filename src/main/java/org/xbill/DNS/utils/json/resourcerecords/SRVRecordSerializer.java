package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.SRVRecord;

import java.io.IOException;

public class SRVRecordSerializer extends AbstractRecordSerializer<SRVRecord> {

	public SRVRecordSerializer() {
		super(SRVRecord.class);
	}

	@Override
	protected void serializeRDataFields(final SRVRecord srvRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeStringField("priority",
				formatNumber(srvRecord.getPriority()));
		jsonGenerator.writeStringField("weight",
				formatNumber(srvRecord.getWeight()));
		jsonGenerator.writeStringField("port",
				formatNumber(srvRecord.getPort()));
		if (srvRecord.getTarget() != null) {
			jsonGenerator.writeStringField("target", srvRecord.getTarget()
					.toString());
		}
	}
}
