package org.xbill.DNS;

/**
 * ZONECNAMERecord Record - maps a zone alias to its real name
 *
 * @author Arnaud Dumont
 */
public class ZONECNAMERecord extends SingleCompressedNameBase {
    private static final long serialVersionUID = 3251461466067595466L;

    ZONECNAMERecord() {}

    @Override
    Record getObject() {
        return new ZONECNAMERecord();
    }

    /**
     * Creates a new ZONECNAMERecord with the given data
     * @param alias The name to which the ZONECNAMERecord alias points
     */
    public ZONECNAMERecord(Name name, int dclass, long ttl, Name alias) {
        super(name, Type.ZONECNAME, dclass, ttl, alias, "alias");
    }

    /**
     * Gets the target of the ZONECNAMERecord Record
     */
    public Name getTarget() {
        return getSingleName();
    }

    /** Gets the alias specified by the ZONECNAMERecord Record */
    public Name getAlias() {
        return getSingleName();
    }
}
