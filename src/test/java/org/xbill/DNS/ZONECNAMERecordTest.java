package org.xbill.DNS;

import junit.framework.TestCase;

public class ZONECNAMERecordTest extends TestCase
{
    public void test_ctor_0arg()
    {
        ZONECNAMERecord d = new ZONECNAMERecord();
        assertNull(d.getName());
        assertNull(d.getTarget());
        assertNull(d.getAlias());
    }

    public void test_ctor_4arg() throws TextParseException
    {
        Name n = Name.fromString("my.name.");
        Name a = Name.fromString("my.alias.");

        ZONECNAMERecord d = new ZONECNAMERecord(n, DClass.IN, 0xABCDEL, a);
        assertEquals(n, d.getName());
        assertEquals(Type.ZONECNAME, d.getType());
        assertEquals(DClass.IN, d.getDClass());
        assertEquals(0xABCDEL, d.getTTL());
        assertEquals(a, d.getTarget());
        assertEquals(a, d.getAlias());
    }

    public void test_getObject()
    {
        ZONECNAMERecord d = new ZONECNAMERecord();
        Record r = d.getObject();
        assertTrue(r instanceof ZONECNAMERecord);
    }
}