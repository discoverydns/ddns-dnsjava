package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.Record;
import org.xbill.DNS.infrastructure.JsonDeserializationExceptionMatcher;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecordTypeReferenceDeserializerTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Mock
	private JsonParser mockJsonParser;
	@Mock
	private ObjectMapper mockObjectMapper;
	@Mock
	private ObjectReader mockObjectReader;
	@Mock
	private DeserializationContext mockDeserializationContext;
	@Mock
	private JsonNode mockJsonNode;
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;

	private ObjectNode fakeObjectNode;
	private RecordTypeReferenceDeserializer recordTypeReferenceDeserializer;
	private String fieldName = "type";
	private String type = "type";

	@Before
	public void setup() throws Throwable {
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);
		
		when(mockJsonParser.getCodec()).thenReturn(mockObjectMapper);
		when(mockObjectMapper.reader()).thenReturn(mockObjectReader);
		when(mockObjectReader.without(DeserializationFeature.UNWRAP_ROOT_VALUE)).thenReturn(mockObjectReader);
		when(mockObjectReader.readTree(mockJsonParser)).thenReturn(
				fakeObjectNode);
		
		when(mockJsonNode.textValue()).thenReturn(type);
		fakeObjectNode.put(fieldName, mockJsonNode);

		recordTypeReferenceDeserializer = new RecordTypeReferenceDeserializer();
	}

	@Test
	public void shouldThrowExceptionIfTypeNodeCannotBeFound() throws Exception {
		fakeObjectNode.put(fieldName, (JsonNode) null);

		thrown.expect(new JsonDeserializationExceptionMatcher(
                JsonDeserializationExceptionCode.missingField,
				new Object[] { fieldName, "resource record" }));

		recordTypeReferenceDeserializer.deserialize(mockJsonParser,
				mockDeserializationContext);
	}

	@Test
	public void shouldThrowExceptionIfFoundTypeIsUnknown() throws Exception {
		thrown.expect(new JsonDeserializationExceptionMatcher(
                JsonDeserializationExceptionCode.unknownResourceRecordType,
				new Object[] { type }));

		recordTypeReferenceDeserializer.deserialize(mockJsonParser,
				mockDeserializationContext);
	}

	@Test
	public void shouldDelegateToObjectMapperIfFoundTypeIsKnown()
			throws Exception {
		recordTypeReferenceDeserializer.registerRecordType(type, Record.class);
		when(mockObjectReader.withType(Record.class)).thenReturn(mockObjectReader);

		recordTypeReferenceDeserializer.deserialize(mockJsonParser,
				mockDeserializationContext);

		verify(mockObjectReader).withType(Record.class);
		verify(mockObjectReader).readValue(fakeObjectNode.toString());
	}
}
