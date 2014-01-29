package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.Name;
import org.xbill.DNS.SPFRecord;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SPFRecordSerializerTest {
	@Mock private JsonGenerator mockJsonGenerator;
	@Mock private SerializerProvider mockSerializerProvider;
	
	private SPFRecordSerializer spfRecordSerializer;
	
	private SPFRecord spfRecord;
    private String string1 = "string1";
	private String string2 = "string 2";

    @Before
	public void setup() throws Throwable {
        List<String> strings = new ArrayList<>();
		strings.add(string1);
		strings.add(string2);
		spfRecord = new SPFRecord(new Name("name.com."), 1, 1, strings);
		
		spfRecordSerializer = new SPFRecordSerializer();
	}
	
	@Test
	public void shouldGenerateStringsFieldAsSingleString() throws Exception {
		spfRecordSerializer.serializeRDataFields(spfRecord, mockJsonGenerator, mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("strings", "\"" + string1 + "\" \"" + string2 + "\"");
	}
}
