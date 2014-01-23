package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.NULLRecord;

import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NULLRecordSerializerTest {
	@Mock private NULLRecord mockNULLRecord;
	@Mock private JsonGenerator mockJsonGenerator;
	@Mock private SerializerProvider mockSerializerProvider;
	
	private NULLRecordSerializer nullRecordSerializer;
	
	@Before
	public void setup() throws Throwable {
		nullRecordSerializer = new NULLRecordSerializer();
	}
	
	@Test
	public void shouldIgnoreNULLRecords() throws Exception {
		nullRecordSerializer.serialize(mockNULLRecord, mockJsonGenerator,
				mockSerializerProvider);
		
		verifyZeroInteractions(mockNULLRecord);
		verifyZeroInteractions(mockJsonGenerator);
		verifyZeroInteractions(mockSerializerProvider);
	}
}
