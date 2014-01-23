package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
import org.xbill.DNS.utils.json.AbstractDeserializer;

import java.io.IOException;

public abstract class AbstractRecordDeserializer<T extends Record> extends
        AbstractDeserializer<T> {
	private static final long serialVersionUID = 1189405106065540372L;

	private static final String NAME_FIELD_NAME = "name";
	private static final String TTL_FIELD_NAME = "ttl";
	private static final String CLASS_FIELD_NAME = "class";
	private static final String RDATA_FIELD_NAME = "rdata";

	protected AbstractRecordDeserializer(final Class<?> recordClass) {
		super(recordClass);
	}

	@Override
    @SuppressWarnings("unchecked")
	public T deserialize(final JsonParser jsonParser,
			final DeserializationContext ctxt) throws IOException,
			JsonProcessingException {
		final ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
		final ObjectNode recordNode = mapper.reader()
				.without(DeserializationFeature.UNWRAP_ROOT_VALUE)
				.readTree(jsonParser);
        if (recordNode.hasNonNull(RDATA_FIELD_NAME)) { //If "rdata" field is detected
            //Delegate to DNSJava to parse "rdata" field
            return (T) Record.fromString(getRecordName(recordNode),
                    Type.value(getNodeStringValue(recordNode, RecordTypeReferenceDeserializer.TYPE_FIELD_NAME)),
                    getDClass(recordNode),
                    getRecordTTL(recordNode),
                    getNodeStringValue(recordNode, RDATA_FIELD_NAME),
                    Name.root);
        } else {
            //Else, parse all type-specific fields
            return createRecord(getRecordName(recordNode), getDClass(recordNode),
                    getRecordTTL(recordNode), recordNode);
        }
	}

	protected abstract T createRecord(Name name, int dclass, long ttl,
			ObjectNode recordNode);

	protected abstract String getTextualRecordType();

	protected String getTextualBeanType() {
		return getTextualRecordType() + " record";
	}

	protected Name getRecordName(final ObjectNode recordNode) {
		return getNodeNameValue(recordNode, NAME_FIELD_NAME);
	}

	protected int getDClass(ObjectNode recordNode) {
		String dClass = getNodeStringValue(recordNode, CLASS_FIELD_NAME);
		return DClass.value(dClass);
	}

	protected int getRecordTTL(final ObjectNode recordNode) {
		return getNodeIntegerValue(recordNode, TTL_FIELD_NAME);
	}
}
