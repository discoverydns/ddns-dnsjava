package org.xbill.DNS.utils.json.resourcerecords;

import java.io.IOException;

import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.utils.base64;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson serializer for the {@link org.xbill.DNS.RRSIGRecord} class
 * 
 * @author Chris Wright
 */
public class RRSIGRecordSerializer extends
		AbstractRecordSerializer<RRSIGRecord> {

	public RRSIGRecordSerializer() {
		super(RRSIGRecord.class);
	}

	@Override
	protected void serializeRDataFields(final RRSIGRecord rrsigRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeNumberField("typeCovered",
				rrsigRecord.getTypeCovered());
		jsonGenerator.writeNumberField("algorithm", rrsigRecord.getAlgorithm());
		jsonGenerator.writeNumberField("labels", rrsigRecord.getLabels());
		jsonGenerator.writeNumberField("originalTTL", rrsigRecord.getOrigTTL());
		jsonGenerator.writeNumberField("expireTime", rrsigRecord.getExpire()
				.getTime() / 1000);
		jsonGenerator.writeNumberField("inceptionTime", rrsigRecord
				.getTimeSigned().getTime() / 1000);
		jsonGenerator.writeNumberField("keyTag", rrsigRecord.getFootprint());
		jsonGenerator.writeStringField("signerName", rrsigRecord.getSigner()
				.toString());
		jsonGenerator.writeStringField("signature",
				base64.toString(rrsigRecord.getSignature()));
	}
}
