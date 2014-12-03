package org.xbill.DNS;

import static org.xbill.DNS.URLRecord.RedirectType;

import java.io.IOException;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class URLRecordTest extends TestCase
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public void test_ctor_0arg()
    {
        URLRecord d = new URLRecord();
        assertNull(d.getName());
        assertEquals(0, d.getRedirectType());
        assertNull(d.getTemplate());
    }

    public void test_ctor_6arg() throws TextParseException {
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

    public void test_ctor_7arg() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";
        int redirectType = RedirectType.REDIRECT_TYPE_301;

        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, template, redirectType);
        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(URLRecord.RedirectType.REDIRECT_TYPE_301, d.getRedirectType());
        assertEquals(template, d.getTemplate());
    }

    public void test_getObject()
    {
        URLRecord d = new URLRecord();
        Record r = d.getObject();
        assertTrue(r instanceof URLRecord);
    }

    public void test_invalidTemplate() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String template = "http://www.url.com/{path}/?{queryParameters}";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Provided template '" + template + "'  is not a valid URI template");

        new URLRecord(n, DClass.IN, 0xABCDEL, template);
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
        assertEquals(URLRecord.RedirectType.REDIRECT_TYPE_301, d.getRedirectType());
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
        assertEquals(URLRecord.RedirectType.REDIRECT_TYPE_302, d.getRedirectType());
        assertEquals(template, d.getTemplate());
    }
}