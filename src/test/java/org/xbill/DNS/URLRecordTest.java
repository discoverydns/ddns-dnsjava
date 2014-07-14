package org.xbill.DNS;

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
}