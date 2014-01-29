package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.net.InetAddresses;
import org.xbill.DNS.AAAARecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.AAAARecord} class
 * @author Arnaud Dumont
 */
public class AAAARecordSerializer extends AbstractRecordSerializer<AAAARecord> {

	public AAAARecordSerializer() {
		super(AAAARecord.class);
	}

	@Override
	protected void serializeRDataFields(final AAAARecord aaaaRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		if (aaaaRecord.getAddress() != null) {
			jsonGenerator.writeStringField("address",
					InetAddresses.toAddrString(aaaaRecord.getAddress()));
		}
	}
}
