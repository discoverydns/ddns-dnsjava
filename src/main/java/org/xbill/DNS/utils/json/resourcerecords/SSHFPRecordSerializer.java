package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.SSHFPRecord;

import java.io.IOException;

public class SSHFPRecordSerializer extends
		AbstractRecordSerializer<SSHFPRecord> {

	public SSHFPRecordSerializer() {
		super(SSHFPRecord.class);
	}

	@Override
	protected void serializeRDataFields(final SSHFPRecord sshfpRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeStringField("algorithm",
				formatNumber(sshfpRecord.getAlgorithm()));
		jsonGenerator.writeStringField("digestType",
				formatNumber(sshfpRecord.getDigestType()));
		jsonGenerator.writeStringField("fingerprint",
				sshfpRecord.getTextualFingerPrint());
	}
}
