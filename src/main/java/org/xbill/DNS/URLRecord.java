package org.xbill.DNS;

import java.io.IOException;
import java.net.URL;

/**
 * URLRecord Record - maps a label to a target URL for HTTP forwarding
 *
 * @author Arnaud Dumont
 */
public class URLRecord extends Record {
    private static final long serialVersionUID = -5287280745661128913L;

    private URL url;
    private boolean pathIncluded;
    private boolean queryParametersIncluded;

    URLRecord() {}

    @Override
    Record getObject() {
        return new URLRecord();
    }

    /**
     * Creates a new URLRecord with the given data
     * @param url The URL the record will redirect to when queried for the name
     * @param pathIncluded If true, the original request's path will be appended to the redirection URL
     * @param queryParametersIncluded If true, the original request's query parameters will be appended to the redirection URL
     */
    public URLRecord(Name name, int dclass, long ttl, URL url, boolean pathIncluded, boolean queryParametersIncluded) {
        super(name, Type.URL, dclass, ttl);
        this.url = url;
        this.pathIncluded = pathIncluded;
        this.queryParametersIncluded = queryParametersIncluded;
    }

    @Override
    void rrFromWire(DNSInput in) throws IOException {
        this.url = new URL(new String(in.readCountedString()));
        this.pathIncluded = in.readU8() == 1;
        this.queryParametersIncluded = in.readU8() == 1;
    }

    @Override
    String rrToString() {
        StringBuffer sb = new StringBuffer();
        if (url != null) {
            sb.append(url.toString());
        }
        sb.append(" ");
        sb.append(pathIncluded);
        sb.append(" ");
        sb.append(queryParametersIncluded);
        return sb.toString();
    }

    @Override
    void rdataFromString(Tokenizer st, Name origin) throws IOException {
        this.url = new URL(st.getString());
        this.pathIncluded = Boolean.parseBoolean(st.getString());
        this.queryParametersIncluded = Boolean.parseBoolean(st.getString());
    }

    @Override
    void rrToWire(DNSOutput out, Compression c, boolean canonical) {
        if (url != null) {
            try {
                out.writeCountedString(byteArrayFromString(url.toString()));
            } catch (TextParseException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        out.writeU8(pathIncluded ? 1 : 0);
        out.writeU8(queryParametersIncluded ? 1 : 0);
    }

    /** Returns the redirection URL
     * @return the URL
     * */
    public URL getURL() {
        return url;
    }

    /** Returns true if the original request's path will be appended to the redirection URL, false otherwise
     * @return the pathIncluded flag
     * */
    public boolean isPathIncluded() {
        return pathIncluded;
    }

    /** Returns true if the original request's query parameters will be appended to the redirection URL, false otherwise
     * @return the queryParametersIncluded flag
     * */
    public boolean isQueryParametersIncluded() {
        return queryParametersIncluded;
    }
}
