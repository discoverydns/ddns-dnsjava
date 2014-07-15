package org.xbill.DNS;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class MAILFWRecordTest extends TestCase
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public void test_ctor_0arg()
    {
        MAILFWRecord d = new  MAILFWRecord();
        assertNull(d.getName());
        assertNull(d.getDestination());
    }

    public void test_ctor_6arg() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String destination = "admin@discoverydns.com";

         MAILFWRecord d = new  MAILFWRecord(n, DClass.IN, 0xABCDEL, destination);
        assertEquals(n, d.getName());
        assertEquals(Type.MAILFW, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(destination, d.getDestination());
    }

    public void test_getObject()
    {
        MAILFWRecord d = new MAILFWRecord();
        Record r = d.getObject();
        assertTrue(r instanceof MAILFWRecord);
    }

    public void test_invalidTemplate() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String destination = "admin@discoverydns.com";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Provided destination '" + destination + "'  is not a valid mail redirection destination");

        new MAILFWRecord(n, DClass.IN, 0xABCDEL, destination);
    }
}