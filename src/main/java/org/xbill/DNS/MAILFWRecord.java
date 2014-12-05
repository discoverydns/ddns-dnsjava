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
    private static final Pattern MAIL_ORIGINAL_RECIPIENT_PATTERN =
            Pattern.compile("^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*)?$",
                    Pattern.CASE_INSENSITIVE);

    private String originalRecipient;
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

    /**
     * Creates a new MAILFWRecord with the given data
     * @param originalRecipient The original mail recipient (left-hand side)
     * @param destination The Mail redirection destination
     */
    public MAILFWRecord(Name name, int dclass, long ttl, String originalRecipient, String destination) {
        this(name, dclass, ttl, destination);
        setOriginalRecipient(originalRecipient);
    }

    @Override
    void rrFromWire(DNSInput in) throws IOException {
        String firstValue = new String(in.readCountedString());
        try {
            final String secondValue = new String(in.readCountedString());
            setOriginalRecipient(firstValue);
            setDestination(secondValue);
        } catch (WireParseException e) {
            setDestination(firstValue);
        }
    }

    @Override
    String rrToString() {
        return (originalRecipient != null ? originalRecipient + " " : "") + destination;
    }

    @Override
    void rdataFromString(Tokenizer st, Name origin) throws IOException {
        String firstValue = st.getString();
        try {
            final String secondValue = st.getString();
            setOriginalRecipient(firstValue);
            setDestination(secondValue);
        } catch (IOException e) {
            setDestination(firstValue);
        }
    }

    @Override
    void rrToWire(DNSOutput out, Compression c, boolean canonical) {
        if (originalRecipient != null) {
            try {
                out.writeCountedString(byteArrayFromString(originalRecipient));
            } catch (TextParseException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
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
    	//TODO FIX THIS!!
//        if (!MAIL_DESTINATION_PATTERN.matcher(destination).matches()) {
//            throw new IllegalArgumentException(
//                    "Provided destination '" + destination + "' is not a valid mail redirection destination");
//        }
        this.destination = destination;
    }

    private void setOriginalRecipient(String originalRecipient) {
        //TODO FIX THIS!!
        /*if (originalRecipient != null && !MAIL_ORIGINAL_RECIPIENT_PATTERN.matcher(originalRecipient).matches()) {
            throw new IllegalArgumentException(
                    "Provided original recipient '" + originalRecipient + "' is not a valid mail recipient");
        }*/
        this.originalRecipient = originalRecipient;
    }

    public String getOriginalRecipient() {
        return originalRecipient;
    }
}
