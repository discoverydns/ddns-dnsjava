package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.xbill.DNS.NULLRecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.NULLRecord} class
 * @author Arnaud Dumont
 */
public class NULLRecordSerializer extends StdSerializer<NULLRecord> {
	public NULLRecordSerializer() {
		super(NULLRecord.class);
	}

	@Override
	public void serialize(final NULLRecord value, final JsonGenerator jgen,
			final SerializerProvider provider) throws IOException,
			JsonGenerationException {
	}
}
