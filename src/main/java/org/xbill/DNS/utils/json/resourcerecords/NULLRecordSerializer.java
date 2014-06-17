package org.xbill.DNS.utils.json.resourcerecords;

import java.io.IOException;

import org.xbill.DNS.NULLRecord;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson serializer for the {@link org.xbill.DNS.NULLRecord} class
 * 
 * @author Arnaud Dumont
 */
public class NULLRecordSerializer extends AbstractRecordSerializer<NULLRecord> {
	public NULLRecordSerializer() {
		super(NULLRecord.class);
	}

	@Override
	protected void serializeRDataFields(NULLRecord value,
			JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonGenerationException {
	}

}
