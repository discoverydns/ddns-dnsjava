package org.xbill.DNS.utils.json.resourcerecords;

import org.xbill.DNS.Name;
import org.xbill.DNS.URLRecord;
import org.xbill.DNS.utils.json.exception.MissingFieldJsonDeserializationException;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Jackson deserializer for the {@link org.xbill.DNS.URLRecord} class.
 * @author Arnaud Dumont
 */
public class URLRecordDeserializer extends AbstractRecordDeserializer<URLRecord> {
    private static final long serialVersionUID = -1483861586185167072L;

    protected URLRecordDeserializer() {
        super(URLRecord.class);
    }

    @Override
    protected URLRecord createRecord(Name name, int dclass, long ttl, ObjectNode recordNode) {
        Integer redirectType;
        final String nodeName = "redirectType";
        try {
            redirectType = getNodeIntegerValue(recordNode, nodeName);
        } catch (MissingFieldJsonDeserializationException e) {
            //For backward compatibility
            return new URLRecord(name, dclass, ttl, getNodeStringValue(recordNode, "template"));
        }

        return new URLRecord(name, dclass, ttl, getNodeStringValue(recordNode, "template"), redirectType,
                getURLNodeValue(recordNode, "title"),
                getURLNodeValue(recordNode, "description"),
                getURLNodeValue(recordNode, "keywords"));
    }

    @Override
    protected String getTextualRecordType() {
        return "URL";
    }

    private String getURLNodeValue(ObjectNode recordNode, String nodeName) {
        String nodeValue;
        try {
            nodeValue = getNodeStringValue(recordNode, nodeName);
        } catch (MissingFieldJsonDeserializationException e) {
            nodeValue = "";
        }
        return nodeValue;
    }

}
