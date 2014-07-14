package org.xbill.DNS.utils.json.resourcerecords;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.MAILFWRecord;
import org.xbill.DNS.Name;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(MockitoJUnitRunner.class)
public class MAILFWRecordDeserializerTest {
    @Mock
    private JsonNode mockDestinationJsonNode;
    @Mock
    private JsonNodeFactory mockJsonNodeFactory;

    private MAILFWRecordDeserializer mailFWRecordDeserializer;

    private ObjectNode fakeObjectNode;
    private String destination = "admin@discoverydns.com";

    @Before
    public void setup() throws Exception {
        fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

        when(mockDestinationJsonNode.textValue()).thenReturn(destination);
        fakeObjectNode.put("destination", mockDestinationJsonNode);

        mailFWRecordDeserializer = new MAILFWRecordDeserializer();
    }

    @Test
    public void shouldReturnTheExpectedTextualRecordType() throws Exception {
        assertEquals("MAILFW", mailFWRecordDeserializer.getTextualRecordType());
    }

    @Test
    public void shouldCreateExpectedRecord() throws Exception {
        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        MAILFWRecord mailfwRecord = mailFWRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

        assertEquals(name, mailfwRecord.getName());
        assertEquals(dclass, mailfwRecord.getDClass());
        assertEquals(ttl, mailfwRecord.getTTL());
        assertEquals(destination, mailfwRecord.getDestination());
    }
}