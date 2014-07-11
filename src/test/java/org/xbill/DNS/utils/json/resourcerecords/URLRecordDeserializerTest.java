package org.xbill.DNS.utils.json.resourcerecords;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URL;

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
    private JsonNode mockURLJsonNode;
    @Mock
    private JsonNode mockPathIncludedJsonNode;
    @Mock
    private JsonNode mockQueryParametersIncludedJsonNode;
    @Mock
    private JsonNodeFactory mockJsonNodeFactory;

    private URLRecordDeserializer urlRecordDeserializer;

    private ObjectNode fakeObjectNode;
    private URL url;
    private boolean pathIncluded = true;
    private boolean queryParametersIncluded = false;

    @Before
    public void setup() throws Exception {
        fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

        url = new URL("http://www.url.com");
        when(mockURLJsonNode.textValue()).thenReturn(url.toString());
        fakeObjectNode.put("url", mockURLJsonNode);

        when(mockPathIncludedJsonNode.booleanValue()).thenReturn(pathIncluded);
        when(mockPathIncludedJsonNode.getNodeType()).thenReturn(JsonNodeType.BOOLEAN);
        fakeObjectNode.put("pathIncluded", mockPathIncludedJsonNode);

        when(mockQueryParametersIncludedJsonNode.booleanValue()).thenReturn(queryParametersIncluded);
        when(mockQueryParametersIncludedJsonNode.getNodeType()).thenReturn(JsonNodeType.BOOLEAN);
        fakeObjectNode.put("queryParametersIncluded", mockQueryParametersIncludedJsonNode);

        urlRecordDeserializer = new URLRecordDeserializer();
    }

    @Test
    public void shouldReturnTheExpectedTextualRecordType() throws Exception {
        assertEquals("URL", urlRecordDeserializer.getTextualRecordType());
    }

    @Test
    public void shouldCreateExpectedRecord() throws Exception {
        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        URLRecord urlRecord = urlRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

        assertEquals(name, urlRecord.getName());
        assertEquals(dclass, urlRecord.getDClass());
        assertEquals(ttl, urlRecord.getTTL());
        assertEquals(url, urlRecord.getURL());
        assertEquals(pathIncluded, urlRecord.isPathIncluded());
        assertEquals(queryParametersIncluded, urlRecord.isQueryParametersIncluded());
    }
}