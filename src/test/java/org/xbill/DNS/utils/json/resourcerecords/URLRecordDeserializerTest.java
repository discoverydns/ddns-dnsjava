package org.xbill.DNS.utils.json.resourcerecords;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
}