package org.xbill.DNS.utils.json.exception;

public class MissingFieldJsonDeserializationException extends JsonDeserializationException {
    private static final long serialVersionUID = -624126988975857636L;

    public MissingFieldJsonDeserializationException(Object... objects) {
        super(JsonDeserializationExceptionCode.missingField, objects);
    }
}
