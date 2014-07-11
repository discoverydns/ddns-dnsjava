package org.xbill.DNS.utils.json.resourcerecords;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;

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

    private URL url;
    private boolean pathIncluded = true;
    private boolean queryParametersIncluded = false;

    @Before
    public void setup() throws Exception {
        url = new URL("http://www.url.com");
        when(mockUrlRecord.getURL()).thenReturn(url);
        when(mockUrlRecord.isPathIncluded()).thenReturn(pathIncluded);
        when(mockUrlRecord.isQueryParametersIncluded()).thenReturn(queryParametersIncluded);

        urlRecordSerializer = new URLRecordSerializer();
    }

    @Test
    public void shouldGenerateUrlField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("url", url.toString());
    }

    @Test
    public void shouldDoNothingIfUrlFieldIsNull() throws Exception {
        when(mockUrlRecord.getURL()).thenReturn(null);

        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator, never()).writeStringField("url", url.toString());
    }

    @Test
    public void shouldGeneratePathIncludedField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeBooleanField("pathIncluded", pathIncluded);
    }

    @Test
    public void shouldGenerateQueryParametersIncludedField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeBooleanField("queryParametersIncluded", queryParametersIncluded);
    }
}