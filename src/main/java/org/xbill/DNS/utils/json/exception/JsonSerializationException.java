package org.xbill.DNS.utils.json.exception;

public class JsonSerializationException extends BaseJsonException {
    private static final long serialVersionUID = -603320986020515945L;

    public enum JsonSerializationExceptionCode {
        /**
         * Thrown when an unexpected error occurs during JSON serialization
         */
        unexpectedResourceRecordGenerationError
    }

    public JsonSerializationException(
            final JsonSerializationExceptionCode exceptionCode,
            final Throwable cause, final Object... objects) {
        super(exceptionCode, cause, objects);
    }

    public JsonSerializationException(
            final JsonSerializationExceptionCode exceptionCode,
            final Object... objects) {
        super(exceptionCode, objects);
    }
}
