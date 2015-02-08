package org.xbill.DNS;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * URLRecord Record - maps a label to a target URL template for HTTP forwarding.
 *
 * @author Arnaud Dumont
 */
public class URLRecord extends Record {
    private static final long serialVersionUID = -5287280745661128913L;

    private static final Pattern URL_TEMPLATE_PATTERN =
            Pattern.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;{}]*[-a-zA-Z0-9+&@#/%=~_|}]$",
                    Pattern.CASE_INSENSITIVE);
    private static final Pattern URL_CLOAKING_PARAMETERS_PATTERN =
            Pattern.compile("^[^<>]*$", Pattern.CASE_INSENSITIVE);
    private String keywords;
    private String description;
    private String title;

    public static final class RedirectType {

        /**
         * HTTP Redirect with status code 302.
         */
        public static final int REDIRECT_TYPE_302 = 0;

        /**
         * HTTP Redirect with status code 301.
         */
        public static final int REDIRECT_TYPE_301 = 1;

        /**
         * HTTP Redirect with status code 303.
         */
        public static final int REDIRECT_TYPE_303 = 2;

        /**
         * HTTP Redirect with status code 307.
         */
        public static final int REDIRECT_TYPE_307 = 3;

        /**
         * URL cloaking with iframe.
         */
        public static final int REDIRECT_TYPE_CLOAKING_IFRAME = 4;

        private static Mnemonic redirectTypes = new Mnemonic("URL Redirection Types",
                Mnemonic.CASE_UPPER);

        static {
            redirectTypes.setMaximum(REDIRECT_TYPE_CLOAKING_IFRAME);
            redirectTypes.setNumericAllowed(true);

            redirectTypes.add(REDIRECT_TYPE_302, "302");
            redirectTypes.add(REDIRECT_TYPE_301, "301");
            redirectTypes.add(REDIRECT_TYPE_303, "303");
            redirectTypes.add(REDIRECT_TYPE_307, "307");
            redirectTypes.add(REDIRECT_TYPE_CLOAKING_IFRAME, "Cloaking");
        }

        private RedirectType() { }

        /**
         * Converts an redirect type into its textual representation.
         */
        public static String
        string(int alg) {
            return redirectTypes.getText(alg);
        }

        /**
         * Converts a textual representation of an redirect type into its numeric
         * code.  Integers in the range 0..REDIRECT_TYPE_CLOAKING_IFRAME are also accepted.
         * @param s The textual representation of the redirect type
         * @return The redirect type code, or -1 on error.
         */
        public static int
        value(String s) {
            return redirectTypes.getValue(s);
        }
    }

    private String template;
    private int redirectType;

    URLRecord() {
    }

    /**
     * Creates a new URLRecord with the given data, with default redirect type (REDIRECT_TYPE_302).
     * @param template The URL template the record will redirect to when queried for the name
     */
    public URLRecord(Name name, int dclass, long ttl, String template) {
        this(name, dclass, ttl, template, RedirectType.REDIRECT_TYPE_302);
    }

    /**
     * Creates a new URLRecord with the given redirect type and data.
     * @param template The URL template the record will redirect to when queried for the name
     */
    public URLRecord(Name name, int dclass, long ttl, String template, int redirectType) {
        super(name, Type.URL, dclass, ttl);

        setTemplate(template);
        this.redirectType = checkU8("redirectType", redirectType);
    }

    /**
     * Creates a new URLRecord with the given redirect type and data.
     * @param template The URL template the record will redirect to when queried for the name
     */
    public URLRecord(Name name, int dclass, long ttl, String template, int redirectType, String title,
                     String description, String keywords) {
        this(name, dclass, ttl, template, redirectType);

        if (redirectType == RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME) {
            setTitle(title);
            setDescription(description);
            setKeywords(keywords);
        }
    }

    @Override
    Record getObject() {
        return new URLRecord();
    }

    @Override
    void rrFromWire(DNSInput in) throws IOException {
        setTemplate(new String(in.readCountedString()));
        try {
            redirectType = in.readU8();
        } catch (WireParseException e) {
            redirectType = RedirectType.REDIRECT_TYPE_302;
        }
        if (redirectType == RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME) {
            try {
                setTitle(new String(in.readCountedString()));
            } catch (IOException e) {
                setTitle(null);
            }
            try {
                setDescription(new String(in.readCountedString()));
            } catch (IOException e) {
                setDescription(null);
            }
            try {
                setKeywords(new String(in.readCountedString()));
            } catch (IOException e) {
                setKeywords(null);
            }
        }

    }

    @Override
    String rrToString() {
        String recordDetails = template + " " + redirectType;
        if (redirectType == RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME) {
            //Have to handle null values as empty strings,
            // otherwise rdataFromString doesn't know which values are missing
            recordDetails = recordDetails + " \""
                    + (title != null ? title : "") + "\" \""
                    + (description != null ? description : "") + "\" \""
                    + (keywords != null ? keywords : "") + "\"";
        }
        return recordDetails.trim();
    }

    @Override
    void rdataFromString(Tokenizer st, Name origin) throws IOException {
        setTemplate(st.getString());
        String redirectTypeString;
        try {
            redirectTypeString = st.getString();
        } catch (IOException e) {
            redirectType = RedirectType.REDIRECT_TYPE_302;
            return;
        }
        redirectType = RedirectType.value(redirectTypeString);
        if (redirectType < 0) {
            throw st.exception("Invalid redirect type: " + redirectTypeString);
        }
        if (redirectType == RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME) {
            try {
                setTitle(st.getString());
            } catch (IOException e) {
                setTitle(null);
            }
            try {
                setDescription(st.getString());
            } catch (IOException e) {
                setDescription(null);
            }
            try {
                setKeywords(st.getString());
            } catch (IOException e) {
                setKeywords(null);
            }
        }
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
        out.writeU8(redirectType);
        if (redirectType == RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME) {
            if (title != null) {
                try {
                    out.writeCountedString(byteArrayFromString(title));
                } catch (TextParseException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
            if (description != null) {
                try {
                    out.writeCountedString(byteArrayFromString(description));
                } catch (TextParseException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
            if (keywords != null) {
                try {
                    out.writeCountedString(byteArrayFromString(keywords));
                } catch (TextParseException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
        }
    }

    /**
     * Returns the redirection type.
     * @return the redirection type
     * */
    public int getRedirectType() {
        return redirectType;
    }

    private void setTemplate(String template) {
        if (!URL_TEMPLATE_PATTERN.matcher(template).matches()) {
            throw new IllegalArgumentException("Provided template '" + template + "' is not a valid URI template");
        }
        this.template = template;
    }

    /**
     * Returns the redirection URL template.
     * @return the redirection URL template
     * */
    public String getTemplate() {
        return template;
    }

    /**
     * Returns the redirection HTML page keywords.
     * @return the redirection HTML page keywords
     * */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Returns the redirection HTML page description.
     * @return the redirection HTML page description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the redirection HTML page title.
     * @return the redirection HTML page title
     * */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && !URL_CLOAKING_PARAMETERS_PATTERN.matcher(title).matches()) {
            throw new IllegalArgumentException("Provided title '" + title + "' is not a valid HTML title");
        }
        this.title = title;
    }

    public void setKeywords(String keywords) {
        if (keywords != null && !URL_CLOAKING_PARAMETERS_PATTERN.matcher(keywords).matches()) {
            throw new IllegalArgumentException("Provided keywords '" + keywords + "' are not valid HTML keywords");
        }
        this.keywords = keywords;
    }

    public void setDescription(String description) {
        if (description != null && !URL_CLOAKING_PARAMETERS_PATTERN.matcher(description).matches()) {
            throw new IllegalArgumentException(
                    "Provided description '" + description + "' is not a valid HTML description");
        }
        this.description = description;
    }
}
