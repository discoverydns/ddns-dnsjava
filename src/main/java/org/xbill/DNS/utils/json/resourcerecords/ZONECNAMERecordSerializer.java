package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.ZONECNAMERecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.ZONECNAMERecord} class
 * @author Arnaud Dumont
 */
public class ZONECNAMERecordSerializer extends
		AbstractRecordSerializer<ZONECNAMERecord> {

	public ZONECNAMERecordSerializer() {
		super(ZONECNAMERecord.class);
	}

	@Override
	protected void serializeRDataFields(final ZONECNAMERecord zoneCNAMERecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		if (zoneCNAMERecord.getTarget() != null) {
			jsonGenerator.writeStringField("target", zoneCNAMERecord.getTarget()
                    .toString());
		}
	}
}
