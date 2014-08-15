package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.Name;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.infrastructure.JsonDeserializationExceptionMatcher;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TXTRecordDeserializerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;
	@Mock
	private JsonNode mockStringsJsonNode;

	private TXTRecordDeserializer txtRecordDeserializer;

	private ObjectNode fakeObjectNode;
    private String string1 = "string1";
	private String string2 = "string 2";
	private String string3 = "string3";
	private String strings = string1 + " \"" + string2 + "\" " + string3;
    private Name name;
    private int dclass = 1;
    private long ttl = 3600L;

	@Before
	public void setup() throws Throwable {
        name = Name.fromString("test.domain.com.");
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

		when(mockStringsJsonNode.textValue()).thenReturn(strings);
		fakeObjectNode.put("strings", mockStringsJsonNode);

		txtRecordDeserializer = new TXTRecordDeserializer();
	}

	@Test
	public void shouldReturnTheExpectedTextualRecordType() throws Exception {
		assertEquals("TXT", txtRecordDeserializer.getTextualRecordType());
	}

	@Test
	public void shouldCreateExpectedRecordWithSingleString() throws Exception {
        when(mockStringsJsonNode.textValue()).thenReturn(string1);
        fakeObjectNode.put("strings", mockStringsJsonNode);

        TXTRecord txtRecord = txtRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);

		assertEquals(name, txtRecord.getName());
		assertEquals(dclass, txtRecord.getDClass());
		assertEquals(ttl, txtRecord.getTTL());
		assertArrayEquals(new String[] { string1 }, txtRecord
				.getStrings().toArray());
	}

	@Test
	public void shouldCreateExpectedRecordWithStringsArray() throws Exception {
        final JsonNode arrayNode =
                new ObjectMapper().readTree("[\"" + string1 + "\",\"" + string2 + "\",\"" + string3 + "\" ]");
        fakeObjectNode.put("strings", arrayNode);

        TXTRecord txtRecord = txtRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);

		assertEquals(name, txtRecord.getName());
		assertEquals(dclass, txtRecord.getDClass());
		assertEquals(ttl, txtRecord.getTTL());
		assertArrayEquals(new String[] { string1, string2, string3 }, txtRecord
				.getStrings().toArray());
	}

    @Test
    public void shouldThrowExceptionIfContainsNonAsciiCharacters() throws Exception {
        when(mockStringsJsonNode.textValue()).thenReturn("first\\naمستخدميb\\nthird");
        txtRecordDeserializer = spy(txtRecordDeserializer);
        String textualBeanType = "textualBeanType";
        when(txtRecordDeserializer.getTextualBeanType()).thenReturn(
                textualBeanType);

        thrown.expect(new JsonDeserializationExceptionMatcher(
                JsonDeserializationExceptionCode.invalidFieldValue,
                new Object[] { "strings", textualBeanType, "Non-ASCII character found" }));

        txtRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);
	}

    @Test
    public void shouldThrowExceptionIfMultipleStringsContainsNonAsciiCharacters() throws Exception {
        final JsonNode arrayNode =
                new ObjectMapper().readTree("[\"" + string1 + "\",\"first\\naمستخدميb\\nthird\",\"" + string3 + "\" ]");
        fakeObjectNode.put("strings", arrayNode);

        txtRecordDeserializer = spy(txtRecordDeserializer);
        String textualBeanType = "textualBeanType";
        when(txtRecordDeserializer.getTextualBeanType()).thenReturn(
                textualBeanType);

        thrown.expect(new JsonDeserializationExceptionMatcher(
                JsonDeserializationExceptionCode.invalidFieldValue,
                new Object[] { "strings", textualBeanType, "Non-ASCII character found" }));

        txtRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);
	}
}
