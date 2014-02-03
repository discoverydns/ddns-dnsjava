package org.xbill.DNS.utils.json.exception;

/**
 * JSON deserialization exception, thrown by the Jackson {@link org.xbill.DNS.Record} deserializers
 * @author Arnaud Dumont
 */
public class JsonDeserializationException extends BaseJsonException {
    private static final long serialVersionUID = -300926710448396734L;

    public enum JsonDeserializationExceptionCode {
        /**
         * Thrown when a required field is missing in the incoming JSON stream
         */
        missingField,
        /**
         * Thrown when an invalid value is received for a field in the incoming JSON stream
         */
        invalidFieldValue,
        /**
         * Thrown when an unexpected error occurs during JSON deserialization
         */
        unexpectedMappingError,
        /**
         * Thrown when a resource record of unknown type is received in the incoming JSON stream
         */
        unknownResourceRecordType
    }

    public JsonDeserializationException(
            final JsonDeserializationExceptionCode exceptionCode,
            final Object... objects) {
        super(exceptionCode, objects);
    }

    public JsonDeserializationException(
            final JsonDeserializationExceptionCode exceptionCode,
            final Throwable cause,
            final Object... objects) {
        super(exceptionCode, cause, objects);
    }
}
