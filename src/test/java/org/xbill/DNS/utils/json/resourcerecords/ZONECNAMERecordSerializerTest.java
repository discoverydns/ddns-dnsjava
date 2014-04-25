package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.ZONECNAMERecord;
import org.xbill.DNS.Name;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZONECNAMERecordSerializerTest {
	@Mock private ZONECNAMERecord mockZONECNAMERecord;
	@Mock private JsonGenerator mockJsonGenerator;
	@Mock private SerializerProvider mockSerializerProvider;
	@Mock private Name mockTargetName;
	
	private ZONECNAMERecordSerializer zoneCNAMERecordSerializer;
	
	private String targetName = "targetName";
	
	@Before
	public void setup() throws Throwable {
		when(mockZONECNAMERecord.getTarget()).thenReturn(mockTargetName);
		when(mockTargetName.toString()).thenReturn(targetName);
		
		zoneCNAMERecordSerializer = new ZONECNAMERecordSerializer();
	}
	
	@Test
	public void shouldGenerateTargetNameField() throws Exception {
		zoneCNAMERecordSerializer.serializeRDataFields(mockZONECNAMERecord, mockJsonGenerator,
                mockSerializerProvider);
		
		verify(mockJsonGenerator).writeStringField("target", targetName);
	}
	
	@Test
	public void shouldDoNothingIfTargetFieldIsNull() throws Exception {
		when(mockZONECNAMERecord.getTarget()).thenReturn(null);

		zoneCNAMERecordSerializer.serializeRDataFields(mockZONECNAMERecord, mockJsonGenerator,
                mockSerializerProvider);
		
		verifyNoMoreInteractions(mockJsonGenerator);
	}
}
