package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.Record;

import java.net.InetAddress;

/**
 * Base Jackson deserializer for the {@link org.xbill.DNS.AAAARecord} and {@link org.xbill.DNS.ARecord} classes
 * @author Arnaud Dumont
 */
public abstract class AbstractAddressRecordDeserializer<T extends Record>
		extends AbstractRecordDeserializer<T> {
	private static final long serialVersionUID = 8261282382283892468L;

	private static final String ADDRESS_FIELD_NAME = "address";

	protected AbstractAddressRecordDeserializer(final Class<?> recordClass) {
		super(recordClass);
	}

    /**
     * Gets the InetAddress from the JSON node's "address" field.
     * @param recordNode The input JSON node
     * @return The corresponding InetAddress
     */
	protected InetAddress getNodeAddressValue(final ObjectNode recordNode) {
		return getNodeAddressValue(recordNode, ADDRESS_FIELD_NAME);
	}
}
