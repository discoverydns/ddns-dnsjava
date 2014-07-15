package org.xbill.DNS;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * URLRecord Record - maps a label to a target URL template for HTTP forwarding
 *
 * @author Arnaud Dumont
 */
public class URLRecord extends Record {
    private static final long serialVersionUID = -5287280745661128913L;

    private static final Pattern URL_TEMPLATE_PATTERN =
            Pattern.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;{}]*[-a-zA-Z0-9+&@#/%=~_|}]$",
                    Pattern.CASE_INSENSITIVE);

    private String template;

    URLRecord() {}

    @Override
    Record getObject() {
        return new URLRecord();
    }

    /**
     * Creates a new URLRecord with the given data
     * @param template The URL template the record will redirect to when queried for the name
     */
    public URLRecord(Name name, int dclass, long ttl, String template) {
        super(name, Type.URL, dclass, ttl);
        setTemplate(template);
    }

    @Override
    void rrFromWire(DNSInput in) throws IOException {
        setTemplate(new String(in.readCountedString()));
    }

    @Override
    String rrToString() {
        return template;
    }

    @Override
    void rdataFromString(Tokenizer st, Name origin) throws IOException {
        setTemplate(st.getString());
    }

    @Override
    void rrToWire(DNSOutput out, Compression c, boolean canonical) {
        if (template != null) {
            try {
                out.writeCountedString(byteArrayFromString(template));
            } catch (TextParseException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    private void setTemplate(String template) {
        if (!URL_TEMPLATE_PATTERN.matcher(template).matches()) {
            throw new IllegalArgumentException("Provided template '" + template + "'  is not a valid URI template");
        }
        this.template = template;
    }

    /** Returns the redirection URL template
     * @return the redirection URL template
     * */
    public String getTemplate() {
        return template;
    }
}
