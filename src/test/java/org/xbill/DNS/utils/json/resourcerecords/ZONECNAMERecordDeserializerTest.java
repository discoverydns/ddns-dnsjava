package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.ZONECNAMERecord;
import org.xbill.DNS.Name;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZONECNAMERecordDeserializerTest {
	@Mock
	private JsonNode mockJsonNode;
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;

	private ZONECNAMERecordDeserializer zoneCNAMERecordDeserializer;

	private ObjectNode fakeObjectNode;
    private Name targetName;

    @Before
	public void setup() throws Throwable {
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

        String target = "target.domain.com.";
        targetName = Name.fromString(target);
		when(mockJsonNode.textValue()).thenReturn(target);
		fakeObjectNode.set("target", mockJsonNode);

		zoneCNAMERecordDeserializer = new ZONECNAMERecordDeserializer();
	}

	@Test
	public void shouldReturnTheExpectedTextualRecordType() throws Exception {
		assertEquals("ZONECNAME", zoneCNAMERecordDeserializer.getTextualRecordType());
	}

	@Test
	public void shouldCreateExpectedRecord() throws Exception {
        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        ZONECNAMERecord zoneCNAMERecord = zoneCNAMERecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

		assertEquals(name, zoneCNAMERecord.getName());
		assertEquals(dclass, zoneCNAMERecord.getDClass());
		assertEquals(ttl, zoneCNAMERecord.getTTL());
		assertEquals(targetName, zoneCNAMERecord.getTarget());
	}
}
