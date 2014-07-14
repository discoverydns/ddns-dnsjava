package org.xbill.DNS.utils.json.resourcerecords;

import org.xbill.DNS.MAILFWRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.URLRecord;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.MAILFWRecord} class
 * @author Arnaud Dumont
 */
public class MAILFWRecordDeserializer extends AbstractRecordDeserializer<MAILFWRecord> {
    private static final long serialVersionUID = 3831802671840433355L;

    protected MAILFWRecordDeserializer() {
        super(URLRecord.class);
    }

    @Override
    protected MAILFWRecord createRecord(Name name, int dclass, long ttl, ObjectNode recordNode) {
        return new MAILFWRecord(name, dclass, ttl,
                getNodeStringValue(recordNode, "destination"));
    }

    @Override
    protected String getTextualRecordType() {
        return "MAILFW";
    }
}
