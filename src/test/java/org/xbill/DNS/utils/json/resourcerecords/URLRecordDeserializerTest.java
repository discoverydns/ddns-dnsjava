package org.xbill.DNS.utils.json.resourcerecords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.Name;
import org.xbill.DNS.URLRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(MockitoJUnitRunner.class)
public class URLRecordDeserializerTest {
    @Mock
    private JsonNode mockRedirectTypeJsonNode;
    @Mock
    private JsonNode mockTemplateJsonNode;
    @Mock
    private JsonNode mockTitleJsonNode;
    @Mock
    private JsonNode mockDescriptionJsonNode;
    @Mock
    private JsonNode mockKeywordsJsonNode;
    @Mock
    private JsonNodeFactory mockJsonNodeFactory;

    private URLRecordDeserializer urlRecordDeserializer;

    private ObjectNode fakeObjectNode;
    private String template = "http://www.url.com/{path}/?{queryParameters}";

    @Before
    public void setup() throws Exception {
        fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

        when(mockTemplateJsonNode.textValue()).thenReturn(template);
        fakeObjectNode.put("template", mockTemplateJsonNode);

        urlRecordDeserializer = new URLRecordDeserializer();
    }

    @Test
    public void shouldReturnTheExpectedTextualRecordType() throws Exception {
        assertEquals("URL", urlRecordDeserializer.getTextualRecordType());
    }

    @Test
    public void shouldCreateExpectedRecord() throws Exception {
        when(mockRedirectTypeJsonNode.getNodeType()).thenReturn(JsonNodeType.NUMBER);
        int redirectType = URLRecord.RedirectType.REDIRECT_TYPE_301;
        when(mockRedirectTypeJsonNode.numberValue()).thenReturn(redirectType);
        fakeObjectNode.put("redirectType", mockRedirectTypeJsonNode);

        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        URLRecord urlRecord = urlRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

        assertEquals(name, urlRecord.getName());
        assertEquals(dclass, urlRecord.getDClass());
        assertEquals(ttl, urlRecord.getTTL());
        assertEquals(redirectType, urlRecord.getRedirectType());
        assertEquals(template, urlRecord.getTemplate());
    }

    @Test
    public void shouldCreateExpectedRecordIfRedirectTypeIsMissing() throws Exception {
        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        URLRecord urlRecord = urlRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

        assertEquals(name, urlRecord.getName());
        assertEquals(dclass, urlRecord.getDClass());
        assertEquals(ttl, urlRecord.getTTL());
        assertEquals(URLRecord.RedirectType.REDIRECT_TYPE_302, urlRecord.getRedirectType());
        assertEquals(template, urlRecord.getTemplate());
    }

    @Test
    public void shouldCreateExpectedIFrameCloakingRecord() throws Exception {
        when(mockRedirectTypeJsonNode.getNodeType()).thenReturn(JsonNodeType.NUMBER);
        int redirectType = URLRecord.RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME;
        when(mockRedirectTypeJsonNode.numberValue()).thenReturn(redirectType);
        fakeObjectNode.put("redirectType", mockRedirectTypeJsonNode);
        String title = "title";
        when(mockTitleJsonNode.textValue()).thenReturn(title);
        fakeObjectNode.put("title", mockTitleJsonNode);
        String description = "description";
        when(mockDescriptionJsonNode.textValue()).thenReturn(description);
        fakeObjectNode.put("description", mockDescriptionJsonNode);
        String keywords = "keywords";
        when(mockKeywordsJsonNode.textValue()).thenReturn(keywords);
        fakeObjectNode.put("keywords", mockKeywordsJsonNode);

        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        URLRecord urlRecord = urlRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

        assertEquals(name, urlRecord.getName());
        assertEquals(dclass, urlRecord.getDClass());
        assertEquals(ttl, urlRecord.getTTL());
        assertEquals(redirectType, urlRecord.getRedirectType());
        assertEquals(template, urlRecord.getTemplate());
        assertEquals(title, urlRecord.getTitle());
        assertEquals(description, urlRecord.getDescription());
        assertEquals(keywords, urlRecord.getKeywords());
    }

    @Test
    public void shouldHandleNullOptionalFields() throws IOException {
        when(mockRedirectTypeJsonNode.getNodeType()).thenReturn(JsonNodeType.NUMBER);
        int redirectType = URLRecord.RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME;
        when(mockRedirectTypeJsonNode.numberValue()).thenReturn(redirectType);
        fakeObjectNode.put("redirectType", mockRedirectTypeJsonNode);
        when(mockTitleJsonNode.textValue()).thenReturn(null);
        fakeObjectNode.put("title", mockTitleJsonNode);
        when(mockDescriptionJsonNode.textValue()).thenReturn(null);
        fakeObjectNode.put("description", mockDescriptionJsonNode);
        when(mockKeywordsJsonNode.textValue()).thenReturn(null);
        fakeObjectNode.put("keywords", mockKeywordsJsonNode);

        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        URLRecord urlRecord = urlRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

        assertEquals(name, urlRecord.getName());
        assertEquals(dclass, urlRecord.getDClass());
        assertEquals(ttl, urlRecord.getTTL());
        assertEquals(redirectType, urlRecord.getRedirectType());
        assertEquals(template, urlRecord.getTemplate());
        assertNull(urlRecord.getTitle());
        assertNull(urlRecord.getDescription());
        assertNull(urlRecord.getKeywords());
    }
}