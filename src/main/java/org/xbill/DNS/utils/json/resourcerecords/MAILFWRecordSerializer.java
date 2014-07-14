package org.xbill.DNS.utils.json.resourcerecords;

import java.io.IOException;

import org.xbill.DNS.MAILFWRecord;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson serializer for the {@link org.xbill.DNS.MAILFWRecord} class
 * @author Arnaud Dumont
 */
public class MAILFWRecordSerializer extends AbstractRecordSerializer<MAILFWRecord> {
    public MAILFWRecordSerializer() {
        super(MAILFWRecord.class);
    }

    @Override
    protected void serializeRDataFields(final MAILFWRecord mailFWRecord, JsonGenerator jsonGenerator, SerializerProvider
            serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStringField("destination", mailFWRecord.getDestination());
    }
}
