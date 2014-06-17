package org.xbill.DNS.utils.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.net.InetAddresses;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException;
import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;

import java.net.InetAddress;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Abstract Jackson deserializer
 * @author Arnaud Dumont
 */
public abstract class AbstractDeserializer<T> extends StdDeserializer<T> {
	private static final long serialVersionUID = 5081830517002430714L;

	protected AbstractDeserializer(final Class<?> vc) {
		super(vc);
	}

    /**
     * Finds the field of given name into the input JSON node or throws the appropriate JsonDeserializationException.
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding JSON node's field node
     */
	protected JsonNode findFieldNode(final ObjectNode recordNode,
			final String fieldName) {
		final JsonNode fieldNode = recordNode.get(fieldName);
		if (fieldNode == null) {
			throw new JsonDeserializationException(
					JsonDeserializationExceptionCode.missingField,
					fieldName, getTextualBeanType());
		}
		return fieldNode;
	}

    /**
     * Returns the string representation of the value of the field of given name into the input JSON node
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding string representation of the field's value
     */
	protected String getNodeStringValue(final ObjectNode recordNode,
			final String fieldName) {
		return findFieldNode(recordNode, fieldName).textValue();
	}

    /**
     * Converts a string representation into a {@link org.xbill.DNS.Name}
     * @param nodeValue The string representation of the node's value
     * @return The corresponding {@link org.xbill.DNS.Name}
     * @throws TextParseException In case the value is not a valid {@link org.xbill.DNS.Name}
     */
	public Name getNameFromString(final String nodeValue)
			throws TextParseException {
		return Name.fromString(nodeValue, Name.root);
	}

    /**
     * Returns the {@link org.xbill.DNS.Name} representation of the value of the field of given name into the input JSON node
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding {@link org.xbill.DNS.Name} representation of the field's value
     */
	protected Name getNodeNameValue(final ObjectNode recordNode,
			final String fieldName) {
		try {
			return getNameFromString(getNodeStringValue(recordNode, fieldName));
		} catch (final TextParseException e) {
			throw new JsonDeserializationException(
					JsonDeserializationExceptionCode.invalidFieldValue, e,
					fieldName, getTextualBeanType(), e.getMessage());
		}
	}

    /**
     * Returns the number representation of the value of the field of given name into the input JSON node
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding number representation of the field's value
     */
	protected Number getNodeNumberValue(final ObjectNode recordNode,
			final String fieldName) {
        JsonNode fieldNode = findFieldNode(recordNode, fieldName);
        switch (fieldNode.getNodeType()) {
            case NUMBER:
                return fieldNode.numberValue();
            case STRING:
                try {
                    return NumberFormat.getInstance(Locale.getDefault()).parse(
                        fieldNode.textValue());
                } catch (final ParseException e) {
                    throw new JsonDeserializationException(
                            JsonDeserializationExceptionCode.invalidFieldValue, e,
                            fieldName, getTextualBeanType(), e.getMessage());
                }
            default:
                throw new JsonDeserializationException(
                        JsonDeserializationExceptionCode.invalidFieldValue,
                        fieldName, getTextualBeanType(), "Field cannot be read as a number");
        }
	}

    /**
     * Returns the long representation of the value of the field of given name into the input JSON node
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding long representation of the field's value
     */
	protected Long getNodeLongValue(final ObjectNode recordNode,
			final String fieldName) {
		return getNodeNumberValue(recordNode, fieldName).longValue();
	}

    /**
     * Returns the integer representation of the value of the field of given name into the input JSON node
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding integer representation of the field's value
     */
	public Integer getNodeIntegerValue(final ObjectNode recordNode,
			final String fieldName) {
		return getNodeNumberValue(recordNode, fieldName).intValue();
	}

    /**
     * Returns the double representation of the value of the field of given name into the input JSON node
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding double representation of the field's value
     */
	protected Double getNodeDoubleValue(final ObjectNode recordNode,
			final String fieldName) {
		return getNodeNumberValue(recordNode, fieldName).doubleValue();
	}

    /**
     * Returns the {@link InetAddress} representation of the value of the field of given name into the input JSON node
     * @param recordNode The input JSON node
     * @param fieldName The given field name
     * @return The corresponding {@link InetAddress} representation of the field's value
     */
	public InetAddress getNodeAddressValue(final ObjectNode recordNode,
			final String fieldName) {
		final JsonNode addressNode = findFieldNode(recordNode, fieldName);
		try {
			if (addressNode.textValue() != null) {
				return getAddressFromString(addressNode.textValue());
			} else {
				return null;
			}
		} catch (final Throwable e) {
			throw new JsonDeserializationException(
					JsonDeserializationExceptionCode.invalidFieldValue, e,
					fieldName, getTextualBeanType(), e.getMessage());
		}
	}

    /**
     * Converts a string representation into an {@link InetAddress}
     * @param address The string representation of the node's value
     * @return The corresponding {@link InetAddress}
     */
	public InetAddress getAddressFromString(final String address) {
		return InetAddresses.forString(address);
	}

    /**
     * @return The textual representation of the bean's type, for logging purpose
     */
	protected abstract String getTextualBeanType();
}
