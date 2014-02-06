package org.xbill.DNS.utils.json.resourcerecords;

import java.io.IOException;

import org.xbill.DNS.DNSKEYRecord;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson serializer for the {@link org.xbill.DNS.DNSKEYRecord} class
 * 
 * @author Chris Wright
 */
public class DNSKEYRecordSerializer extends
		AbstractRecordSerializer<DNSKEYRecord> {

	public DNSKEYRecordSerializer() {
		super(DNSKEYRecord.class);
	}

	@Override
	protected void serializeRDataFields(final DNSKEYRecord dnskeyRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {

		jsonGenerator.writeStringField("flags",
				formatNumber(dnskeyRecord.getFlags()));
		jsonGenerator.writeStringField("protocol",
				formatNumber(dnskeyRecord.getProtocol()));
		jsonGenerator.writeStringField("algorithm",
				formatNumber(dnskeyRecord.getAlgorithm()));
		jsonGenerator.writeStringField("key",
				dnskeyRecord.getTextualPublicKey());
	}
}
