package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.ARecord;

import java.net.InetAddress;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ARecordSerializerTest {
	@Mock private ARecord mockARecord;
	@Mock private JsonGenerator mockJsonGenerator;
	@Mock private SerializerProvider mockSerializerProvider;
	@Mock private InetAddress mockInetAddress;
	
	private ARecordSerializer aRecordSerializer;
	private String address = "address";
	
	@Before
	public void setup() throws Throwable {
		when(mockARecord.getAddress()).thenReturn(mockInetAddress);
		when(mockInetAddress.getHostAddress()).thenReturn(address);
		
		aRecordSerializer = new ARecordSerializer();
	}
	
	@Test
	public void shouldGenerateAddressField() throws Exception {
		aRecordSerializer.serializeRDataFields(mockARecord, mockJsonGenerator, mockSerializerProvider);
		
		verify(mockJsonGenerator).writeStringField("address", address);
	}
	
	@Test
	public void shouldDoNothingIfAddressFieldIsNull() throws Exception {
		when(mockARecord.getAddress()).thenReturn(null);

		aRecordSerializer.serializeRDataFields(mockARecord, mockJsonGenerator, mockSerializerProvider);
		
		verifyNoMoreInteractions(mockJsonGenerator);
	}
}
