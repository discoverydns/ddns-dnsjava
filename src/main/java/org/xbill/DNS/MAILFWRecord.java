package org.xbill.DNS;

import java.io.IOException;
import java.util.regex.Pattern;

public class MAILFWRecord extends Record {
    private static final long serialVersionUID = -368229832408733122L;

    private static final Pattern MAIL_DESTINATION_PATTERN =
            Pattern.compile("^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*)?" +
                            "@([a-zA-Z0-9_]([a-zA-Z0-9-_]{0,61}[a-zA-Z0-9_])?\\.)+" +
                            "[a-zA-Z0-9_][a-zA-Z0-9-_]{0,4}[a-zA-Z0-9_]$",
                    Pattern.CASE_INSENSITIVE);

    private String destination;

    MAILFWRecord() {}

    @Override
    Record getObject() {
        return new MAILFWRecord();
    }


    /**
     * Creates a new MAILFWRecord with the given data
     * @param destination The Mail redirection destination
     */
    public MAILFWRecord(Name name, int dclass, long ttl, String destination) {
        super(name, Type.MAILFW, dclass, ttl);
        setDestination(destination);
    }

    @Override
    void rrFromWire(DNSInput in) throws IOException {
        setDestination(new String(in.readCountedString()));
    }

    @Override
    String rrToString() {
        return destination;
    }

    @Override
    void rdataFromString(Tokenizer st, Name origin) throws IOException {
        setDestination(st.getString());
    }

    @Override
    void rrToWire(DNSOutput out, Compression c, boolean canonical) {
        if (destination != null) {
            try {
                out.writeCountedString(byteArrayFromString(destination));
            } catch (TextParseException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    /** Returns the mail redirection destination
     * @return the mail redirection destination
     * */
    public String getDestination() {
        return destination;
    }

    private void setDestination(String destination) {
        if (!MAIL_DESTINATION_PATTERN.matcher(destination).matches()) {
            throw new IllegalArgumentException(
                    "Provided destination '" + destination + "'  is not a valid mail redirection destination");
        }
        this.destination = destination;
    }
}
