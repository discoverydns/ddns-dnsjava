package org.xbill.DNS.utils.json.resourcerecords;

import org.xbill.DNS.Name;
import org.xbill.DNS.URLRecord;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
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
        try {
            redirectType = getNodeIntegerValue(recordNode, "redirectType");
        } catch (MissingFieldJsonDeserializationException e) {
            //For backward compatibility
            return new URLRecord(name, dclass, ttl, getNodeStringValue(recordNode, "template"));
        }

        return new URLRecord(name, dclass, ttl, getNodeStringValue(recordNode, "template"), redirectType,
                getOptionalNodeStringValue(recordNode, "title"),
                getOptionalNodeStringValue(recordNode, "description"),
                getOptionalNodeStringValue(recordNode, "keywords"));
    }

    @Override
    protected String getTextualRecordType() {
        return "URL";
    }

    private String getOptionalNodeStringValue(ObjectNode recordNode, String nodeName) {
        String nodeValue;
        try {
            nodeValue = getNodeStringValue(recordNode, nodeName);
            checkStringLength(nodeValue, nodeName);
        } catch (MissingFieldJsonDeserializationException e) {
            nodeValue = null;
        }
        return nodeValue;
    }

    private void checkStringLength(String string, String field) {
        if (string != null && string.length() > 255) {
            throw new JsonDeserializationException(
                    JsonDeserializationException.JsonDeserializationExceptionCode.invalidFieldValue,
                    field, getTextualBeanType(), "String too long. Maximum is 255 characters");
        }
    }

}
