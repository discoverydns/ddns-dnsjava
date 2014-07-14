package org.xbill.DNS.utils.json.resourcerecords;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.URLRecord;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@RunWith(MockitoJUnitRunner.class)
public class URLRecordSerializerTest {
    @Mock
    private JsonGenerator mockJsonGenerator;
    @Mock private SerializerProvider mockSerializerProvider;
    @Mock
    private URLRecord mockUrlRecord;

    private URLRecordSerializer urlRecordSerializer;

    private String template = "http://www.url.com/{path}/?{queryParameters}";

    @Before
    public void setup() throws Exception {
        when(mockUrlRecord.getTemplate()).thenReturn(template);

        urlRecordSerializer = new URLRecordSerializer();
    }

    @Test
    public void shouldGenerateUrlField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("template", template);
    }
}