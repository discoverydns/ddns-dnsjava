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
import org.xbill.DNS.SPFRecord;
import org.xbill.DNS.infrastructure.JsonDeserializationExceptionMatcher;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SPFRecordDeserializerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private JsonNodeFactory mockJsonNodeFactory;
    @Mock
    private JsonNode mockStringsJsonNode;

    private SPFRecordDeserializer spfRecordDeserializer;

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
        fakeObjectNode.set("strings", mockStringsJsonNode);

        spfRecordDeserializer = new SPFRecordDeserializer();
    }

    @Test
    public void shouldReturnTheExpectedTextualRecordType() throws Exception {
        assertEquals("SPF", spfRecordDeserializer.getTextualRecordType());
    }

    @Test
    public void shouldCreateExpectedRecordWithSingleString() throws Exception {
        when(mockStringsJsonNode.textValue()).thenReturn(string1);
        fakeObjectNode.set("strings", mockStringsJsonNode);

        SPFRecord spfRecord = spfRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);

        assertEquals(name, spfRecord.getName());
        assertEquals(dclass, spfRecord.getDClass());
        assertEquals(ttl, spfRecord.getTTL());
        assertArrayEquals(new String[] { string1 }, spfRecord
                .getStrings().toArray());
    }

    @Test
    public void shouldCreateExpectedRecordWithStringsArray() throws Exception {
        final JsonNode arrayNode =
                new ObjectMapper().readTree("[\"" + string1 + "\",\"" + string2 + "\",\"" + string3 + "\" ]");
        fakeObjectNode.set("strings", arrayNode);

        SPFRecord spfRecord = spfRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);

        assertEquals(name, spfRecord.getName());
        assertEquals(dclass, spfRecord.getDClass());
        assertEquals(ttl, spfRecord.getTTL());
        assertArrayEquals(new String[] { string1, string2, string3 }, spfRecord
                .getStrings().toArray());
    }

    @Test
    public void shouldThrowExceptionIfContainsNonAsciiCharacters() throws Exception {
        when(mockStringsJsonNode.textValue()).thenReturn("first\\naمستخدميb\\nthird");
        spfRecordDeserializer = spy(spfRecordDeserializer);
        String textualBeanType = "textualBeanType";
        when(spfRecordDeserializer.getTextualBeanType()).thenReturn(
                textualBeanType);

        thrown.expect(new JsonDeserializationExceptionMatcher(
                JsonDeserializationException.JsonDeserializationExceptionCode.invalidFieldValue,
                new Object[] { "strings", textualBeanType, "Non-ASCII character found" }));

        spfRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);
    }

    @Test
    public void shouldThrowExceptionIfMultipleStringsContainsNonAsciiCharacters() throws Exception {
        final JsonNode arrayNode =
                new ObjectMapper().readTree("[\"" + string1 + "\",\"first\\naمستخدميb\\nthird\",\"" + string3 + "\" ]");
        fakeObjectNode.set("strings", arrayNode);

        spfRecordDeserializer = spy(spfRecordDeserializer);
        String textualBeanType = "textualBeanType";
        when(spfRecordDeserializer.getTextualBeanType()).thenReturn(
                textualBeanType);

        thrown.expect(new JsonDeserializationExceptionMatcher(
                JsonDeserializationException.JsonDeserializationExceptionCode.invalidFieldValue,
                new Object[] { "strings", textualBeanType, "Non-ASCII character found" }));

        spfRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);
    }
}
