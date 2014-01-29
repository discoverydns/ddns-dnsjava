package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.TLSARecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.TLSARecord} class
 * @author Arnaud Dumont
 */
public class TLSARecordSerializer extends AbstractRecordSerializer<TLSARecord> {

	public TLSARecordSerializer() {
		super(TLSARecord.class);
	}

	@Override
	protected void serializeRDataFields(final TLSARecord tlsaRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeStringField("certificateUsage",
				formatNumber(tlsaRecord.getCertificateUsage()));
		jsonGenerator.writeStringField("selector",
				formatNumber(tlsaRecord.getSelector()));
		jsonGenerator.writeStringField("matchingType",
				formatNumber(tlsaRecord.getMatchingType()));
		jsonGenerator.writeStringField("certificateAssociationData",
				tlsaRecord.getTextualCertificateAssociationData());
	}
}
