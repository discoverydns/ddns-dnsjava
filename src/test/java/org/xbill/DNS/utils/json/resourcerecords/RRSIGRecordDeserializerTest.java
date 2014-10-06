package org.xbill.DNS.utils.json.resourcerecords;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.utils.base64;

public class RRSIGRecordDeserializerTest {

    @Test
    public void shouldReturnTheExpectedTextualRecordType() throws Exception {
        assertEquals("RRSIG", new RRSIGRecordDeserializer().getTextualRecordType());
    }

    @Test
    public void shouldCreateExpectedRecord() throws Exception {
        String signatureString = "SR4xELpW+7DCmOeNtyxsm6KmSWY9" +
                "+q5dQ7IZDkxzxyk5QUAYhPimc8X0A5emr85ZyX3bxq5Xe51DsdOs8lbxqSfVirrfYzz3fLj4" +
                "/BU7OKqIdhVRJyhoTthW1RcT0dJUyXR/aPhzW0iidQ+4O6K0t0PV2Y3rBNRJ5tb4wJCSAUpw96SBn76BrNvA8oIxDQBN";
        JsonNode objectNode = new ObjectMapper().readTree("{\"name\":\"signedimported.zone.\","
                + "\"class\":\"IN\","
                + "\"ttl\":\"3600\","
                + "\"type\":\"RRSIG\","
                + "\"typeCovered\":6,"
                + "\"algorithm\":8,"
                + "\"labels\":2,"
                + "\"originalTTL\":3601,"
                + "\"expireTime\":1414906926,"
                + "\"inceptionTime\":1412487726,"
                + "\"keyTag\":24628,"
                + "\"signerName\":\"signer.zone.\","
                + "\"signature\":\"" + signatureString + "\"}");

        Name name = Name.fromString("signedimported.zone.");
        int dclass = 1;
        long ttl = 3600L;
        RRSIGRecord rrsigRecord = new RRSIGRecordDeserializer().createRecord(name,
                dclass, ttl, (ObjectNode) objectNode);

        assertEquals(name, rrsigRecord.getName());
        assertEquals(dclass, rrsigRecord.getDClass());
        assertEquals(ttl, rrsigRecord.getTTL());
        assertEquals(6, rrsigRecord.getTypeCovered());
        assertEquals(8, rrsigRecord.getAlgorithm());
        assertEquals(2, rrsigRecord.getLabels());
        assertEquals(3601, rrsigRecord.getOrigTTL());
        assertEquals(new Date(1414906926), rrsigRecord.getExpire());
        assertEquals(new Date(1412487726), rrsigRecord.getTimeSigned());
        assertEquals(Name.fromString("signer.zone."), rrsigRecord.getSigner());
        assertEquals(signatureString, base64.toString(rrsigRecord.getSignature()));
        assertEquals(24628, rrsigRecord.getFootprint());


    }

}