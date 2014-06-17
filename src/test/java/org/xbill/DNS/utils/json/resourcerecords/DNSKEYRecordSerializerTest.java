package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.DNSKEYRecord;
import org.xbill.DNS.Name;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DNSKEYRecordSerializerTest {
    @Mock private JsonGenerator mockJsonGenerator;
    @Mock private SerializerProvider mockSerializerProvider;

    private DNSKEYRecordSerializer dnskeyRecordSerializer;

    private DNSKEYRecord dnskeyRecord;
    private int flags = 256;
    private int protocol = 3;
    private int algorithm = 8;
    private String key = "4MS4SFN10P02UCOG4M1AMDC41CAO1ITQ";

    @Before
    public void setup() throws Exception {
        dnskeyRecord = new DNSKEYRecord(new Name("name.com."), 1, 1, flags, protocol, algorithm, key);

        dnskeyRecordSerializer = new DNSKEYRecordSerializer();
    }

    @Test
    public void shouldGenerateFormattedFlagsField() throws Exception {
        String formattedFlags = "formattedFlags";
        dnskeyRecordSerializer = spy(dnskeyRecordSerializer);
        when(dnskeyRecordSerializer.formatNumber(flags)).thenReturn(formattedFlags);

        dnskeyRecordSerializer.serializeRDataFields(dnskeyRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("flags", formattedFlags);
    }

    @Test
    public void shouldGenerateFormattedProtocolField() throws Exception {
        String formattedProtocol = "formattedProtocol";
        dnskeyRecordSerializer = spy(dnskeyRecordSerializer);
        when(dnskeyRecordSerializer.formatNumber(protocol)).thenReturn(formattedProtocol);

        dnskeyRecordSerializer.serializeRDataFields(dnskeyRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("protocol", formattedProtocol);
    }

    @Test
    public void shouldGenerateFormattedAlgorithmField() throws Exception {
        String formattedAlgorithm = "formattedAlgorithm";
        dnskeyRecordSerializer = spy(dnskeyRecordSerializer);
        when(dnskeyRecordSerializer.formatNumber(algorithm)).thenReturn(formattedAlgorithm);

        dnskeyRecordSerializer.serializeRDataFields(dnskeyRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("algorithm", formattedAlgorithm);
    }

    @Test
    public void shouldGenerateCertField() throws Exception {
        dnskeyRecordSerializer.serializeRDataFields(dnskeyRecord, mockJsonGenerator,
                mockSerializerProvider);

        verify(mockJsonGenerator).writeStringField("key", key);
    }
}
