package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
import org.xbill.DNS.infrastructure.JsonSerializationExceptionMatcher;
import org.xbill.DNS.utils.json.exception.JsonSerializationException;
import org.xbill.DNS.utils.json.exception.JsonSerializationException.JsonSerializationExceptionCode;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractRecordSerializerTest {
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private AbstractRecordSerializer<Record> abstractRecordSerializer;

	@Mock private Record mockRecord;
	@Mock private JsonGenerator mockJsonGenerator;
	@Mock private SerializerProvider mockSerializerProvider;
	@Mock private Name mockName;
	
	private String name = "name";
	private Long ttl = 1L;
    private Integer type = 2;

    @Before
	public void setup() throws Throwable {
		when(mockRecord.getName()).thenReturn(mockName);
		when(mockName.toString()).thenReturn(name);
		when(mockRecord.getTTL()).thenReturn(ttl);
		when(mockRecord.getType()).thenReturn(type);
		when(mockRecord.getDClass()).thenReturn(DClass.IN);
		
		abstractRecordSerializer = new AbstractRecordSerializer<Record>(Record.class){
			@Override
			protected void serializeRDataFields(Record value,
					JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonGenerationException {
			}
		};
	}
	
	@Test
	public void shouldWriteExpectedObjectForFullyExpandedRData() throws Exception {
		abstractRecordSerializer = spy(abstractRecordSerializer);
        String formattedTTL = "formattedTTL";
        when(abstractRecordSerializer.formatNumber(ttl)).thenReturn(formattedTTL);
        ResourceRecordSerializationStrategy.setGlobalSerializationStrategy(
                ResourceRecordSerializationStrategy.SerializationStrategy.ALWAYS_FULLY_EXPANDED_RDATA);
		
		abstractRecordSerializer.serialize(mockRecord, mockJsonGenerator, mockSerializerProvider);
		
		verify(mockJsonGenerator).writeStartObject();
		verify(mockJsonGenerator).writeStringField("name", name);
		verify(mockJsonGenerator).writeStringField("class", "IN");
		verify(mockJsonGenerator).writeStringField("ttl", formattedTTL);
		verify(mockJsonGenerator).writeStringField("type", "NS");
		verify(mockJsonGenerator).writeEndObject();
	}
	
	@Test
	public void shouldDelegateToChildClassToWriteRDataFieldsForFullyExpandedRData() throws Exception {
        abstractRecordSerializer = spy(abstractRecordSerializer);
        ResourceRecordSerializationStrategy.setGlobalSerializationStrategy(
                ResourceRecordSerializationStrategy.SerializationStrategy.ALWAYS_FULLY_EXPANDED_RDATA);

        abstractRecordSerializer.serialize(mockRecord, mockJsonGenerator, mockSerializerProvider);
		
		verify(abstractRecordSerializer).serializeRDataFields(mockRecord, mockJsonGenerator,
				mockSerializerProvider);
        verify(mockRecord, times(0)).rdataToString();
	}

    @Test
    public void shouldWriteExpectedObjectForRawRData() throws Exception {
        abstractRecordSerializer = spy(abstractRecordSerializer);
        String formattedTTL = "formattedTTL";
        when(abstractRecordSerializer.formatNumber(ttl)).thenReturn(formattedTTL);
        ResourceRecordSerializationStrategy.setGlobalSerializationStrategy(
                ResourceRecordSerializationStrategy.SerializationStrategy.ALWAYS_RAW_RDATA);
        String rawRdata = "rawRdata";
        when(mockRecord.rdataToString()).thenReturn(rawRdata);

        abstractRecordSerializer.serialize(mockRecord, mockJsonGenerator, mockSerializerProvider);

        verify(mockJsonGenerator).writeStartObject();
        verify(mockJsonGenerator).writeStringField("name", name);
        verify(mockJsonGenerator).writeStringField("class", "IN");
        verify(mockJsonGenerator).writeStringField("ttl", formattedTTL);
        verify(mockJsonGenerator).writeStringField("type", "NS");
        verify(mockJsonGenerator).writeStringField("rdata", rawRdata);
        verify(mockJsonGenerator).writeEndObject();
        verify(abstractRecordSerializer, times(0)).serializeRDataFields(mockRecord, mockJsonGenerator,
                mockSerializerProvider);
    }

	@Test
	public void shouldRethrowThrownJsonSerializationExceptionWhenGeneratingNode() throws Exception {
		final JsonSerializationException exception = new JsonSerializationException(
                JsonSerializationExceptionCode.unexpectedResourceRecordGenerationError,
                new Exception("cause"), "arguments");
		doThrow(exception).when(mockJsonGenerator).writeStartObject();
		
		thrown.expect(new BaseMatcher<Throwable>() {
			@Override
			public boolean matches(Object item) {
				return exception.equals(item);
			}

			@Override
			public void describeTo(Description description) {}
		});
		
		abstractRecordSerializer.serialize(mockRecord, mockJsonGenerator, mockSerializerProvider);
	}
	
	@Test
	public void shouldWrapThrownExceptionIfNotJsonSerializationExceptionWhenGeneratingNode() throws Exception {
		IOException exception = new IOException("message");
		doThrow(exception).when(mockJsonGenerator).writeStartObject();
		
		thrown.expect(new JsonSerializationExceptionMatcher(
                JsonSerializationExceptionCode.unexpectedResourceRecordGenerationError,
                exception, new Object[] {Type.string(type), name, exception.getMessage()}));
		
		abstractRecordSerializer.serialize(mockRecord, mockJsonGenerator, mockSerializerProvider);
	}

    @Test
    public void shouldEscapeCharacterStringIfNecessary() throws Exception {
        String stringWithoutSpace = "string1";
        String stringWithSpace = "string 2";

        assertEquals(stringWithoutSpace,
                abstractRecordSerializer.escapeCharacterString(stringWithoutSpace));
        assertEquals("\"" + stringWithSpace + "\"",
                abstractRecordSerializer.escapeCharacterString(stringWithSpace));
    }
}
