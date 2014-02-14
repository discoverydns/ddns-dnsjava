package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.DNSKEYRecord;
import org.xbill.DNS.Name;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DNSKEYRecordDeserializerTest {
    @Mock
    private JsonNode mockFlagsJsonNode;
    @Mock
    private JsonNode mockProtocolJsonNode;
    @Mock
    private JsonNode mockAlgorithmJsonNode;
    @Mock
    private JsonNode mockKeyJsonNode;
    @Mock
    private JsonNodeFactory mockJsonNodeFactory;

    private DNSKEYRecordDeserializer dnskeyRecordDeserializer;

    private ObjectNode fakeObjectNode;
    private Name name;
    private int flags = 256;
    private int protocol = 3;
    private int algorithm = 8;
    private String key = "4MS4SFN10P02UCOG4M1AMDC41CAO1ITQ";

    @Before
    public void setup() throws Throwable {
        fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

        name = Name.fromString("test.domain.com.");

        when(mockFlagsJsonNode.textValue()).thenReturn(
                String.valueOf(flags));
        when(mockFlagsJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
        fakeObjectNode.put("flags", mockFlagsJsonNode);

        when(mockProtocolJsonNode.textValue()).thenReturn(String.valueOf(protocol));
        when(mockProtocolJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
        fakeObjectNode.put("protocol", mockProtocolJsonNode);

        when(mockAlgorithmJsonNode.textValue()).thenReturn(
                String.valueOf(algorithm));
        when(mockAlgorithmJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
        fakeObjectNode.put("algorithm", mockAlgorithmJsonNode);

        when(mockKeyJsonNode.textValue()).thenReturn(key);
        fakeObjectNode.put("key", mockKeyJsonNode);

        dnskeyRecordDeserializer = new DNSKEYRecordDeserializer();
    }

    @Test
    public void shouldReturnTheExpectedTextualRecordType() throws Exception {
        assertEquals("DNSKEY", dnskeyRecordDeserializer.getTextualRecordType());
    }

    @Test
    public void shouldCreateExpectedRecord() throws Exception {
        int dclass = 1;
        long ttl = 3600L;
        DNSKEYRecord dnskeyRecord = dnskeyRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

        assertEquals(name, dnskeyRecord.getName());
        assertEquals(dclass, dnskeyRecord.getDClass());
        assertEquals(ttl, dnskeyRecord.getTTL());
        assertEquals(flags, dnskeyRecord.getFlags());
        assertEquals(protocol, dnskeyRecord.getProtocol());
        assertEquals(algorithm, dnskeyRecord.getAlgorithm());
        assertEquals(key, dnskeyRecord.getTextualPublicKey());
    }
}
