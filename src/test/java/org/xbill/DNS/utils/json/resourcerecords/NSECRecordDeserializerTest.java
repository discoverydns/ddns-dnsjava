package org.xbill.DNS.utils.json.resourcerecords;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.xbill.DNS.NSECRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.Type;

public class NSECRecordDeserializerTest {
    @Test
    public void shouldReturnTheExpectedTextualRecordType() throws Exception {
        assertEquals("NSEC", new NSECRecordDeserializer().getTextualRecordType());
    }

    @Test
    public void shouldCreateExpectedRecord() throws Exception {
        JsonNode objectNode = new ObjectMapper().readTree(
                                        "{\"name\":\"signedimported.zone.\","
                                        + "\"class\":\"IN\","
                                        + "\"ttl\":\"600\","
                                        + "\"type\":\"NSEC\","
                                        + "\"nextDomain\":\"abc.signedimported.zone.\","
                                        + "\"types\":\"NS SOA RRSIG NSEC DNSKEY\"}");

        Name name = Name.fromString("signedimported.zone.");
        int dclass = 1;
        long ttl = 600L;
        NSECRecord rrsigRecord = new NSECRecordDeserializer().createRecord(name,
                dclass, ttl, (ObjectNode) objectNode);

        assertEquals(name, rrsigRecord.getName());
        assertEquals(dclass, rrsigRecord.getDClass());
        assertEquals(ttl, rrsigRecord.getTTL());
        assertEquals(Name.fromString("abc.signedimported.zone."), rrsigRecord.getNext());
        assertArrayEquals(new int[]{Type.NS, Type.SOA, Type.RRSIG, Type.NSEC, Type.DNSKEY}, rrsigRecord.getTypes());


    }

}