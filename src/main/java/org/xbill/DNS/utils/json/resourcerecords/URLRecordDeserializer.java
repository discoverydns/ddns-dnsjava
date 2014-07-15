package org.xbill.DNS.utils.json.resourcerecords;

import org.xbill.DNS.Name;
import org.xbill.DNS.URLRecord;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.URLRecord} class
 * @author Arnaud Dumont
 */
public class URLRecordDeserializer extends AbstractRecordDeserializer<URLRecord> {
    private static final long serialVersionUID = -1483861586185167072L;

    protected URLRecordDeserializer() {
        super(URLRecord.class);
    }

    @Override
    protected URLRecord createRecord(Name name, int dclass, long ttl, ObjectNode recordNode) {
        return new URLRecord(name, dclass, ttl,
                getNodeStringValue(recordNode, "template"));
    }

    @Override
    protected String getTextualRecordType() {
        return "URL";
    }
}
