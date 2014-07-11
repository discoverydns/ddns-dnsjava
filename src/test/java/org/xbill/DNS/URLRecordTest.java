package org.xbill.DNS;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

public class URLRecordTest extends TestCase
{
    public void test_ctor_0arg()
    {
        URLRecord d = new URLRecord();
        assertNull(d.getName());
        assertNull(d.getURL());
        assertFalse(d.isPathIncluded());
        assertFalse(d.isQueryParametersIncluded());
    }

    public void test_ctor_6arg() throws TextParseException, MalformedURLException {
        Name n = Name.fromString("my.name.");
        URL url = new URL("http://www.url.com");
        boolean pathIncluded = true;
        boolean queryParametersIncluded = false;

        URLRecord d = new URLRecord(n, DClass.IN, 0xABCDEL, url, pathIncluded, queryParametersIncluded);
        assertEquals(n, d.getName());
        assertEquals(Type.URL, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(url, d.getURL());
        assertEquals(pathIncluded, d.isPathIncluded());
        assertEquals(queryParametersIncluded, d.isQueryParametersIncluded());
    }

    public void test_getObject()
    {
        URLRecord d = new URLRecord();
        Record r = d.getObject();
        assertTrue(r instanceof URLRecord);
    }
}