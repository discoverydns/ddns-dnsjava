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
	private String string2 = "string 2";

    @Before
	public void setup() throws Throwable {
        List<String> strings = new ArrayList<String>();
		strings.add(string1);
		strings.add(string2);
		txtRecord = new TXTRecord(new Name("name.com."), 1, 1, strings);
		
		txtRecordSerializer = new TXTRecordSerializer();
	}
	
	@Test
	public void shouldGenerateStringsFieldAsSingleString() throws Exception {
		txtRecordSerializer.serializeRDataFields(txtRecord, mockJsonGenerator, mockSerializerProvider);
		
		verify(mockJsonGenerator).writeStringField("strings", "\"" + string1 + "\" \"" + string2 + "\"");
	}
}
