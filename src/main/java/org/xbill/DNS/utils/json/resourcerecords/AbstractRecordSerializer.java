package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
import org.xbill.DNS.utils.json.exception.JsonSerializationException;
import org.xbill.DNS.utils.json.exception.JsonSerializationException.JsonSerializationExceptionCode;

import java.io.IOException;

/**
 * Base Jackson serializer for the {@link org.xbill.DNS.Record} class
 * @author Arnaud Dumont
 */
public abstract class AbstractRecordSerializer<T extends Record> extends
		StdSerializer<T> {

    public AbstractRecordSerializer(final Class<T> t) {
		super(t);
    }

	@Override
	public void serialize(final T value, final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) {
		try {
			jsonGenerator.writeStartObject();

			// Common fields
			jsonGenerator.writeStringField("name", value.getName().toString());
			jsonGenerator.writeStringField("class",
					DClass.string(value.getDClass()));
			jsonGenerator.writeStringField("ttl", formatNumber(value.getTTL()));
			jsonGenerator
					.writeStringField("type", Type.string(value.getType()));

			// RDATA fields
            if (ResourceRecordSerializationStrategy.isRawRDataFormat()) {
                jsonGenerator.writeStringField("rdata", value.rdataToString());
            } else {
			    serializeRDataFields(value, jsonGenerator, serializerProvider);
            }

			jsonGenerator.writeEndObject();
		} catch (final IOException e) {
			throw new JsonSerializationException(
					JsonSerializationExceptionCode.unexpectedResourceRecordGenerationError,
					e, Type.string(value.getType()),
					value.getName() != null ? value.getName().toString()
							: "unknown", e.getMessage());
		}
	}

    /**
     * Serializes the expanded version of the RData fields
     * @param value The record to serialise
     * @param jsonGenerator The current Jackson JsonGenerator
     * @param serializerProvider The current Jackson serializerProvider
     * @throws IOException In case of any I/O error during serialization
     * @throws JsonGenerationException In case of any JSON errors during serialization
     */
	protected abstract void serializeRDataFields(T value,
			JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonGenerationException;

    /**
     * Formats the given number into output JSON textual representation
     * @param number The number to format
     * @return The corresponding output JSON textual representation
     */
	public String formatNumber(final Number number) {
		return String.valueOf(number);
	}
}
