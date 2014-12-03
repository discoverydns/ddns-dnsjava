package org.xbill.DNS.utils.json.resourcerecords;

import java.io.IOException;

import org.xbill.DNS.URLRecord;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson serializer for the {@link org.xbill.DNS.URLRecord} class.
 * @author Arnaud Dumont
 */
public class URLRecordSerializer extends AbstractRecordSerializer<URLRecord> {
    public URLRecordSerializer() {
        super(URLRecord.class);
    }

    @Override
    protected void serializeRDataFields(final URLRecord urlRecord, JsonGenerator jsonGenerator, SerializerProvider
            serializerProvider) throws IOException {
        jsonGenerator.writeStringField("template", urlRecord.getTemplate());
        jsonGenerator.writeStringField("redirectType", formatNumber(urlRecord.getRedirectType()));
        jsonGenerator.writeStringField("title", urlRecord.getTitle());
        jsonGenerator.writeStringField("description", urlRecord.getDescription());
        jsonGenerator.writeStringField("keywords", urlRecord.getKeywords());
    }
}
