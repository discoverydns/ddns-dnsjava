package org.xbill.DNS.infrastructure;


import org.xbill.DNS.utils.json.exception.JsonDeserializationException.JsonDeserializationExceptionCode;
import org.xbill.DNS.utils.json.exception.MissingFieldJsonDeserializationException;

public class MissingFieldJsonDeserializationExceptionMatcher extends JsonDeserializationExceptionMatcher {
    public MissingFieldJsonDeserializationExceptionMatcher(Object[] objects) {
        super(JsonDeserializationExceptionCode.missingField, objects);
    }

    @Override
    public boolean matches(Object item) {
        if (item == null
                || !MissingFieldJsonDeserializationException.class.isAssignableFrom(item.getClass())) {
            return false;
        }
        return super.matches(item);
    }
}
