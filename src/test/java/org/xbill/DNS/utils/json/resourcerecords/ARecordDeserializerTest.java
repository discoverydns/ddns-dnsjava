package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.net.InetAddresses;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Name;

import java.net.InetAddress;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ARecordDeserializerTest {
	@Mock
	private JsonNode mockJsonNode;
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;

	private ARecordDeserializer aRecordDeserializer;

	private ObjectNode fakeObjectNode;
	private Name name;
    private InetAddress inetaddress;

	@Before
	public void setup() throws Throwable {
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

		name = Name.fromString("test.domain.com.");
        String address = "1.2.3.4";
        inetaddress = InetAddresses.forString(address);
		when(mockJsonNode.textValue()).thenReturn(address);
		fakeObjectNode.set("address", mockJsonNode);

		aRecordDeserializer = new ARecordDeserializer();
	}

	@Test
	public void shouldReturnTheExpectedTextualRecordType() throws Exception {
		assertEquals("A", aRecordDeserializer.getTextualRecordType());
	}

	@Test
	public void shouldCreateExpectedRecord() throws Exception {
        int dclass = 1;
        long ttl = 3600L;
        ARecord aRecord = aRecordDeserializer.createRecord(name, dclass, ttl,
				fakeObjectNode);

		assertEquals(name, aRecord.getName());
		assertEquals(dclass, aRecord.getDClass());
		assertEquals(ttl, aRecord.getTTL());
		assertEquals(inetaddress, aRecord.getAddress());
	}
}
