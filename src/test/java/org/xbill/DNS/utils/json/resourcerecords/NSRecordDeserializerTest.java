package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Name;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NSRecordDeserializerTest {
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;
	@Mock
	private JsonNode mockJsonNode;

	private NSRecordDeserializer nsRecordDeserializer;

	private ObjectNode fakeObjectNode;
    private Name targetName;

    @Before
	public void setup() throws Throwable {
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

        String target = "target.domain.com.";
        targetName = Name.fromString(target);
		when(mockJsonNode.textValue()).thenReturn(target);
		fakeObjectNode.set("target", mockJsonNode);

		nsRecordDeserializer = new NSRecordDeserializer();
	}

	@Test
	public void shouldReturnTheExpectedTextualRecordType() throws Exception {
		assertEquals("NS", nsRecordDeserializer.getTextualRecordType());
	}

	@Test
	public void shouldCreateExpectedRecord() throws Exception {
        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        NSRecord nsRecord = nsRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);

		assertEquals(name, nsRecord.getName());
		assertEquals(dclass, nsRecord.getDClass());
		assertEquals(ttl, nsRecord.getTTL());
		assertEquals(targetName, nsRecord.getTarget());
	}
}
