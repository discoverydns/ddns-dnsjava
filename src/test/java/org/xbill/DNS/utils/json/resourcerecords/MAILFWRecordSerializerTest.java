package org.xbill.DNS.utils.json.resourcerecords;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.MAILFWRecord;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@RunWith(MockitoJUnitRunner.class)
public class MAILFWRecordSerializerTest {
    @Mock
    private JsonGenerator mockJsonGenerator;
    @Mock private SerializerProvider mockSerializerProvider;
    @Mock
    private MAILFWRecord mockMailFWRecord;

    private MAILFWRecordSerializer mailfwRecordSerializer;

    private String destination = "admin@discoverydns.com";

    @Before
    public void setup() throws Exception {
        when(mockMailFWRecord.getDestination()).thenReturn(destination);

        mailfwRecordSerializer = new MAILFWRecordSerializer();
    }

    @Test
    public void shouldGenerateUrlField() throws Exception {
        mailfwRecordSerializer.serializeRDataFields(mockMailFWRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("destination", destination);
    }
}