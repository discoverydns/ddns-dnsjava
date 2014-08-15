package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.Name;
import org.xbill.DNS.TXTRecord;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TXTRecordSerializerTest {
	@Mock private JsonGenerator mockJsonGenerator;
	@Mock private SerializerProvider mockSerializerProvider;
	
	private TXTRecordSerializer txtRecordSerializer;
	
	private TXTRecord txtRecord;
    private String string1 = "string1";

    @Before
	public void setup() throws Throwable {
		txtRecordSerializer = new TXTRecordSerializer();
	}

    @Test
    public void shouldGenerateSingleStringsFieldAsSingleString() throws Exception {
        txtRecord = new TXTRecord(new Name("name.com."), 1, 1, string1);

        txtRecordSerializer.serializeRDataFields(txtRecord, mockJsonGenerator, mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("strings", string1);
    }
	
	@Test
	public void shouldGenerateMultipleStringsFieldAsArrayOfStrings() throws Exception {
        List<String> strings = new ArrayList<String>();
        strings.add(string1);
        String string2 = "string 2";
        strings.add(string2);
        txtRecord = new TXTRecord(new Name("name.com."), 1, 1, strings);

        txtRecordSerializer.serializeRDataFields(txtRecord, mockJsonGenerator, mockSerializerProvider);

        verify(mockJsonGenerator).writeArrayFieldStart("strings");
		verify(mockJsonGenerator).writeString(string1);
        verify(mockJsonGenerator).writeString(string2);
        verify(mockJsonGenerator).writeEndArray();
	}
}
