package org.xbill.DNS.utils.json.resourcerecords;

import static org.mockito.Mockito.spy;
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

    private int redirectType = URLRecord.RedirectType.REDIRECT_TYPE_301;
    private String template = "http://www.url.com/{path}/?{queryParameters}";
    private String title = "title";
    private String description = "description";
    private String keywords = "keywords";

    @Before
    public void setup() throws Exception {
        when(mockUrlRecord.getRedirectType()).thenReturn(redirectType);
        when(mockUrlRecord.getTemplate()).thenReturn(template);
        when(mockUrlRecord.getTitle()).thenReturn(title);
        when(mockUrlRecord.getDescription()).thenReturn(description);
        when(mockUrlRecord.getKeywords()).thenReturn(keywords);

        urlRecordSerializer = new URLRecordSerializer();
    }

    @Test
    public void shouldGenerateUrlField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("template", template);
    }

    @Test
    public void shouldGenerateRedirectTypeField() throws Exception {
        String formattedRedirectType = "formattedRedirectType";
        urlRecordSerializer = spy(urlRecordSerializer);
        when(urlRecordSerializer.formatNumber(redirectType)).thenReturn(formattedRedirectType);

        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("redirectType", formattedRedirectType);
    }

    @Test
    public void shouldGenerateTitleField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("title", title);
    }

    @Test
    public void shouldGenerateDescriptionField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("description", description);
    }

    @Test
    public void shouldGenerateKeywordsField() throws Exception {
        urlRecordSerializer.serializeRDataFields(mockUrlRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("keywords", keywords);
    }
}