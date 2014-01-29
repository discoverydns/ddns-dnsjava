package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.CERTRecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.CERTRecord} class
 * @author Arnaud Dumont
 */
public class CERTRecordSerializer extends AbstractRecordSerializer<CERTRecord> {

	public CERTRecordSerializer() {
		super(CERTRecord.class);
	}

	@Override
	protected void serializeRDataFields(final CERTRecord certRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeStringField("certType",
				formatNumber(certRecord.getCertType()));
		jsonGenerator.writeStringField("keyTag",
				formatNumber(certRecord.getKeyTag()));
		jsonGenerator.writeStringField("algorithm",
				formatNumber(certRecord.getAlgorithm()));
		jsonGenerator.writeStringField("cert",
				certRecord.getTextualCert());
	}
}
