package org.xbill.DNS.utils.json.resourcerecords;

import org.xbill.DNS.MAILFWRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.URLRecord;
import org.xbill.DNS.utils.json.exception.MissingFieldJsonDeserializationException;

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
        String originalRecipient;
        try {
            originalRecipient = getNodeStringValue(recordNode, "originalRecipient");
        } catch (MissingFieldJsonDeserializationException e) {
            //For backward compatibility
            return new MAILFWRecord(name, dclass, ttl, getNodeStringValue(recordNode, "destination"));
        }
        return new MAILFWRecord(name, dclass, ttl, originalRecipient,
                getNodeStringValue(recordNode, "destination"));
    }

    @Override
    protected String getTextualRecordType() {
        return "MAILFW";
    }
}
