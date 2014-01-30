package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.DSRecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.DSRecord} class
 * @author Arnaud Dumont
 */
public class DSRecordSerializer extends AbstractRecordSerializer<DSRecord> {

	public DSRecordSerializer() {
		super(DSRecord.class);
	}

	@Override
	protected void serializeRDataFields(final DSRecord dsRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeStringField("footprint",
				formatNumber(dsRecord.getFootprint()));
		jsonGenerator.writeStringField("algorithm",
				formatNumber(dsRecord.getAlgorithm()));
		jsonGenerator.writeStringField("digestId",
				formatNumber(dsRecord.getDigestID()));
		jsonGenerator.writeStringField("digest",
				dsRecord.getTextualDigest());
	}
}
