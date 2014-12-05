package org.xbill.DNS;

import junit.framework.TestCase;

public class MAILFWRecordTest extends TestCase
{
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
        assertNull(d.getOriginalRecipient());
    }

    public void test_ctor_7arg() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String originalRecipient = "originalRecipient";
        String destination = "admin@discoverydns.com";

        MAILFWRecord d = new  MAILFWRecord(n, DClass.IN, 0xABCDEL, originalRecipient, destination);
        assertEquals(n, d.getName());
        assertEquals(Type.MAILFW, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(originalRecipient, d.getOriginalRecipient());
        assertEquals(destination, d.getDestination());
    }

    public void test_getObject()
    {
        MAILFWRecord d = new MAILFWRecord();
        Record r = d.getObject();
        assertTrue(r instanceof MAILFWRecord);
    }

    //TODO fix this
    /*public void test_invalidDestination() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String destination = "<invalid> destination";

        try {
            new MAILFWRecord(n, DClass.IN, 0xABCDEL, destination);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Provided destination '" + destination + "' is not a valid mail redirection destination",
                    e.getMessage());
        }
    }*/

    //TODO fix this
    /*public void test_invalidOriginalRecipient() throws TextParseException {
        Name n = Name.fromString("my.name.");
        String originalRecipient = "invalid@recipient";
        String destination = "admin@discoverydns.com";

        try {
            new MAILFWRecord(n, DClass.IN, 0xABCDEL, originalRecipient, destination);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Provided original recipient '" + originalRecipient + "' is not a valid mail recipient",
                    e.getMessage());
        }
    }*/
}