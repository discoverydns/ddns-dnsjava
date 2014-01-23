package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Record;

import java.net.InetAddress;

public abstract class AbstractAddressRecordDeserializer<T extends Record>
		extends AbstractRecordDeserializer<T> {
	private static final long serialVersionUID = 8261282382283892468L;

	private static final String ADDRESS_FIELD_NAME = "address";

	protected AbstractAddressRecordDeserializer(final Class<?> recordClass) {
		super(recordClass);
	}

	protected InetAddress getNodeAddressValue(final ObjectNode recordNode) {
		return getNodeAddressValue(recordNode, ADDRESS_FIELD_NAME);
	}
}
