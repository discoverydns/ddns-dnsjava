package org.xbill.DNS;

import static org.xbill.DNS.URLRecord.RedirectType;

import java.io.IOException;

import junit.framework.TestCase;

public class URLRecordTest extends TestCase
{
    public void test_ctor_0arg()
    {
        URLRecord d = new URLRecord();
        assertNull(d.getName());
        assertEquals(0, d.getRedirectType());
        assertNull(d.getTemplate());
    }

    public void test_ctor_4arg() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";

        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, template);
        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(RedirectType.REDIRECT_TYPE_302, d.getRedirectType());
        assertEquals(template, d.getTemplate());
    }

    public void test_ctor_5arg() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_301;

        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, template, redirectType);
        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(RedirectType.REDIRECT_TYPE_301, d.getRedirectType());
        assertEquals(template, d.getTemplate());
    }

    public void test_ctor_8arg() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME;
        String title = "title";
        String description = "description";
        String keywords = "keywords";

        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, template, redirectType, title, description, keywords);
        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME, d.getRedirectType());
        assertEquals(template, d.getTemplate());
        assertEquals(title, d.getTitle());
        assertEquals(description, d.getDescription());
        assertEquals(keywords, d.getKeywords());
    }

    public void test_ctor_8arg_null_opt_params() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME;

        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, template, redirectType, null, null, null);
        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME, d.getRedirectType());
        assertEquals(template, d.getTemplate());
        assertNull(d.getTitle());
        assertNull(d.getDescription());
        assertNull(d.getKeywords());
    }

    public void test_getObject()
    {
        URLRecord d = new URLRecord();
        Record r = d.getObject();
        assertTrue(r instanceof URLRecord);
    }

    public void test_invalidTemplate() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "notAtemplate";

        try {
            new URLRecord(n, DClass.IN, 0xABCDEL, template);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Provided template '" + template + "' is not a valid URI template", e.getMessage());
        }
    }

    public void test_RDataFromString() throws IOException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_301;

        URLRecord d = (URLRecord) URLRecord.fromString(n, Type.URL, DClass.IN, 0xABCDEL,
                template + " " + redirectType, Name.root);

        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(RedirectType.REDIRECT_TYPE_301, d.getRedirectType());
        assertEquals(template, d.getTemplate());
    }

    public void test_RDataFromStringWithOptionalFields() throws IOException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME;

        String title = "title";
        String description = "description here";
        String keywords = "keywords";
        URLRecord d = (URLRecord) URLRecord.fromString(n, Type.URL, DClass.IN, 0xABCDEL,
                template + " " + redirectType + " " + title + " \"" + description + "\" " + keywords, Name.root);

        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME, d.getRedirectType());
        assertEquals(template, d.getTemplate());
        assertEquals(title, d.getTitle());
        assertEquals(description, d.getDescription());
        assertEquals(keywords, d.getKeywords());
    }

    public void test_RDataFromStringWithoutRedirectType() throws IOException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";

        URLRecord d = (URLRecord) URLRecord.fromString(n, Type.URL, DClass.IN, 0xABCDEL,
                template, Name.root);

        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(RedirectType.REDIRECT_TYPE_302, d.getRedirectType());
        assertEquals(template, d.getTemplate());
    }

    public void test_invalidTitle() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        String title = "title <with> tags";

        try {
            new URLRecord(n, DClass.IN, 0xABCDEL, template, RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME,
                    title, "description", "keywords");
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Provided title '" + title + "' is not a valid HTML title", e.getMessage());
        }
    }

    public void test_invalidDescription() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        String description = "description <with> tags";

        try {
            new URLRecord(n, DClass.IN, 0xABCDEL, template, RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME,
                    "title", description, "keywords");
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Provided description '" + description + "' is not a valid HTML description", e.getMessage());
        }
    }

    public void test_invalidKeywords() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        String keywords = "keywords <with> tags";

        try {
            new URLRecord(n, DClass.IN, 0xABCDEL, template, RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME,
                    "title", "description", keywords);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Provided keywords '" + keywords + "' are not valid HTML keywords", e.getMessage());
        }
    }

    public void test_rrToAndFromWire_null_opt_params() throws IOException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME;
        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, template, redirectType, null, null, null);
        DNSOutput out = new DNSOutput();
        d.rrToWire(out, null, true);

        d.rrFromWire(new DNSInput(out.toByteArray()));

        assertEquals(redirectType, d.getRedirectType());
        assertEquals(template, d.getTemplate());
        assertNull(d.getTitle());
        assertNull(d.getDescription());
        assertNull(d.getKeywords());
    }

    public void test_rdataToAndFromString_null_opt_params() throws IOException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_CLOAKING_IFRAME;
        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, template, redirectType, null, null, null);

        final String rdataToString = d.rdataToString();
        assertEquals(template + " " + redirectType + " \"\" \"\" \"\"", rdataToString);

        d.rdataFromString(new Tokenizer(rdataToString), n);

        assertEquals(redirectType, d.getRedirectType());
        assertEquals(template, d.getTemplate());
        assertEquals("", d.getTitle());
        assertEquals("", d.getDescription());
        assertEquals("", d.getKeywords());
    }
}