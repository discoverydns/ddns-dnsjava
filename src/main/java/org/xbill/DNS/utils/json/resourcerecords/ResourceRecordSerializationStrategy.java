package org.xbill.DNS.utils.json.resourcerecords;

public class ResourceRecordSerializationStrategy {

    public enum SerializationStrategy {
        /**
         * The Record's RData will be split into separate Json fields,
         * depending on the record's type, and this whatever request input parameter is used. This is the default.
         */
        ALWAYS_FULLY_EXPANDED_RDATA,
        /**
         * The Record's RData will be presented as a single textual "rdata" Json field,
         * whatever request input parameter is used.
         */
        ALWAYS_RAW_RDATA,
        /**
         * The Record's RData will be either presented as a single textual "rdata" Json field if the request's "rDataFormat" is "raw",
         * or split into separate fields, depending on the record's type, otherwise.
         */
        PER_REQUEST
    }

    private static SerializationStrategy globalSerializationStrategy = SerializationStrategy.ALWAYS_FULLY_EXPANDED_RDATA;

    private static ThreadLocal<Boolean> currentThreadLocalSerializationStrategy = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    static void setGlobalSerializationStrategy(SerializationStrategy globalSerializationStrategy) {
        ResourceRecordSerializationStrategy.globalSerializationStrategy = globalSerializationStrategy;
    }

    public static void setRawRDataFormat(Boolean rawRDataFormat) {
        if (globalSerializationStrategy == SerializationStrategy.PER_REQUEST) {
		    currentThreadLocalSerializationStrategy.set(rawRDataFormat);
        }
	}

	public static Boolean isRawRDataFormat() {
        switch (globalSerializationStrategy) {
            case ALWAYS_FULLY_EXPANDED_RDATA:
                return false;
            case ALWAYS_RAW_RDATA:
                return true;
            case PER_REQUEST:
                return currentThreadLocalSerializationStrategy.get();
        }
        return false;
	}
}
