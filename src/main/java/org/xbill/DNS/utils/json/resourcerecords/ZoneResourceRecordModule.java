package org.xbill.DNS.utils.json.resourcerecords;

import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CERTRecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.DNSKEYRecord;
import org.xbill.DNS.DSRecord;
import org.xbill.DNS.LOCRecord;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NAPTRRecord;
import org.xbill.DNS.NSECRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.NULLRecord;
import org.xbill.DNS.PTRRecord;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.SOARecord;
import org.xbill.DNS.SPFRecord;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.SSHFPRecord;
import org.xbill.DNS.TLSARecord;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.Type;
import org.xbill.DNS.URLRecord;
import org.xbill.DNS.ZONECNAMERecord;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module for {@link org.xbill.DNS.Record} serializers and deserializers
 * 
 * @author Arnaud Dumont
 */
public class ZoneResourceRecordModule extends SimpleModule {
	private static final long serialVersionUID = -138356800817009942L;

	public ZoneResourceRecordModule(String name) {
		this(
				name,
				ResourceRecordSerializationStrategy.SerializationStrategy.ALWAYS_FULLY_EXPANDED_RDATA);
	}

	public ZoneResourceRecordModule(
			String name,
			ResourceRecordSerializationStrategy.SerializationStrategy serializationStrategy) {
		super(name);
		ResourceRecordSerializationStrategy
				.setGlobalSerializationStrategy(serializationStrategy);

		addDeserializer(Record.class, recordTypeReferenceDeserializer());

		// Register managed record types' deserializers here
		addDeserializer(ARecord.class, aRecordDeserializer());
		addDeserializer(NSRecord.class, nsRecordDeserializer());
		addDeserializer(SOARecord.class, soaRecordDeserializer());
		addDeserializer(AAAARecord.class, aaaaRecordDeserializer());
		addDeserializer(MXRecord.class, mxRecordDeserializer());
		addDeserializer(CNAMERecord.class, cnameRecordDeserializer());
		addDeserializer(DNSKEYRecord.class, dnskeyRecordDeserializer());
		addDeserializer(TXTRecord.class, txtRecordDeserializer());
		addDeserializer(SRVRecord.class, srvRecordDeserializer());
		addDeserializer(NAPTRRecord.class, naptrRecordDeserializer());
		addDeserializer(SPFRecord.class, spfRecordDeserializer());
		addDeserializer(DSRecord.class, dsRecordDeserializer());
		addDeserializer(CERTRecord.class, certRecordDeserializer());
		addDeserializer(PTRRecord.class, ptrRecordDeserializer());
		addDeserializer(SSHFPRecord.class, sshfpRecordDeserializer());
		addDeserializer(TLSARecord.class, tlsaRecordDeserializer());
		addDeserializer(LOCRecord.class, locRecordDeserializer());
		addDeserializer(ZONECNAMERecord.class, zoneCNAMERecordDeserializer());
		addDeserializer(URLRecord.class, urlRecordDeserializer());

		// Register managed record types' serializers here
		addSerializer(ARecord.class, aRecordSerializer());
		addSerializer(NSRecord.class, nsRecordSerializer());
		addSerializer(SOARecord.class, soaRecordSerializer());
		addSerializer(AAAARecord.class, aaaaRecordSerializer());
		addSerializer(MXRecord.class, mxRecordSerializer());
		addSerializer(CNAMERecord.class, cnameRecordSerializer());
		addSerializer(DNSKEYRecord.class, dnskeyRecordSerializer());
		addSerializer(TXTRecord.class, txtRecordSerializer());
		addSerializer(SRVRecord.class, srvRecordSerializer());
		addSerializer(NAPTRRecord.class, naptrRecordSerializer());
		addSerializer(SPFRecord.class, spfRecordSerializer());
		addSerializer(DSRecord.class, dsRecordSerializer());
		addSerializer(CERTRecord.class, certRecordSerializer());
		addSerializer(PTRRecord.class, ptrRecordSerializer());
		addSerializer(NULLRecord.class, nullRecordSerializer());
		addSerializer(SSHFPRecord.class, sshfpRecordSerializer());
		addSerializer(TLSARecord.class, tlsaRecordSerializer());
		addSerializer(LOCRecord.class, locRecordSerializer());
		addSerializer(RRSIGRecord.class, rrsigRecordSerializer());
		addSerializer(NSECRecord.class, nsecRecordSerializer());
		addSerializer(ZONECNAMERecord.class, zoneCNAMERecordSerializer());
		addSerializer(URLRecord.class, urlRecordSerializer());
	}

	private RecordTypeReferenceDeserializer recordTypeReferenceDeserializer() {
		final RecordTypeReferenceDeserializer recordTypeReferenceDeserializer = new RecordTypeReferenceDeserializer();

		// Register managed record types here
		recordTypeReferenceDeserializer.registerRecordType(Type.string(Type.A),
				ARecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.NS), NSRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.SOA), SOARecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.AAAA), AAAARecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.MX), MXRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.CNAME), CNAMERecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.TXT), TXTRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.SRV), SRVRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.NAPTR), NAPTRRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.SPF), SPFRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.DS), DSRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.CERT), CERTRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.PTR), PTRRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.DNSKEY), DNSKEYRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.SSHFP), SSHFPRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.TLSA), TLSARecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.LOC), LOCRecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.ZONECNAME), ZONECNAMERecord.class);
		recordTypeReferenceDeserializer.registerRecordType(
				Type.string(Type.URL), URLRecord.class);

		return recordTypeReferenceDeserializer;
	}

	private ARecordDeserializer aRecordDeserializer() {
		return new ARecordDeserializer();
	}

	private ARecordSerializer aRecordSerializer() {
		return new ARecordSerializer();
	}

	private NSRecordDeserializer nsRecordDeserializer() {
		return new NSRecordDeserializer();
	}

	private NSRecordSerializer nsRecordSerializer() {
		return new NSRecordSerializer();
	}

	private SOARecordDeserializer soaRecordDeserializer() {
		return new SOARecordDeserializer();
	}

	private SOARecordSerializer soaRecordSerializer() {
		return new SOARecordSerializer();
	}

	private DNSKEYRecordDeserializer dnskeyRecordDeserializer() {
		return new DNSKEYRecordDeserializer();
	}

	private DNSKEYRecordSerializer dnskeyRecordSerializer() {
		return new DNSKEYRecordSerializer();
	}

	private AAAARecordDeserializer aaaaRecordDeserializer() {
		return new AAAARecordDeserializer();
	}

	private AAAARecordSerializer aaaaRecordSerializer() {
		return new AAAARecordSerializer();
	}

	private MXRecordDeserializer mxRecordDeserializer() {
		return new MXRecordDeserializer();
	}

	private MXRecordSerializer mxRecordSerializer() {
		return new MXRecordSerializer();
	}

	private CNAMERecordDeserializer cnameRecordDeserializer() {
		return new CNAMERecordDeserializer();
	}

	private CNAMERecordSerializer cnameRecordSerializer() {
		return new CNAMERecordSerializer();
	}

	private TXTRecordDeserializer txtRecordDeserializer() {
		return new TXTRecordDeserializer();
	}

	private TXTRecordSerializer txtRecordSerializer() {
		return new TXTRecordSerializer();
	}

	private SRVRecordDeserializer srvRecordDeserializer() {
		return new SRVRecordDeserializer();
	}

	private SRVRecordSerializer srvRecordSerializer() {
		return new SRVRecordSerializer();
	}

	private NAPTRRecordDeserializer naptrRecordDeserializer() {
		return new NAPTRRecordDeserializer();
	}

	private NAPTRRecordSerializer naptrRecordSerializer() {
		return new NAPTRRecordSerializer();
	}

	private SPFRecordDeserializer spfRecordDeserializer() {
		return new SPFRecordDeserializer();
	}

	private SPFRecordSerializer spfRecordSerializer() {
		return new SPFRecordSerializer();
	}

	private DSRecordDeserializer dsRecordDeserializer() {
		return new DSRecordDeserializer();
	}

	private DSRecordSerializer dsRecordSerializer() {
		return new DSRecordSerializer();
	}

	private CERTRecordDeserializer certRecordDeserializer() {
		return new CERTRecordDeserializer();
	}

	private CERTRecordSerializer certRecordSerializer() {
		return new CERTRecordSerializer();
	}

	private PTRRecordDeserializer ptrRecordDeserializer() {
		return new PTRRecordDeserializer();
	}

	private PTRRecordSerializer ptrRecordSerializer() {
		return new PTRRecordSerializer();
	}

	private NULLRecordSerializer nullRecordSerializer() {
		return new NULLRecordSerializer();
	}

	private SSHFPRecordDeserializer sshfpRecordDeserializer() {
		return new SSHFPRecordDeserializer();
	}

	private SSHFPRecordSerializer sshfpRecordSerializer() {
		return new SSHFPRecordSerializer();
	}

	private TLSARecordDeserializer tlsaRecordDeserializer() {
		return new TLSARecordDeserializer();
	}

	private TLSARecordSerializer tlsaRecordSerializer() {
		return new TLSARecordSerializer();
	}

	private LOCRecordDeserializer locRecordDeserializer() {
		return new LOCRecordDeserializer();
	}

	private LOCRecordSerializer locRecordSerializer() {
		return new LOCRecordSerializer();
	}

	private RRSIGRecordSerializer rrsigRecordSerializer() {
		return new RRSIGRecordSerializer();
	}

	private NSECRecordSerializer nsecRecordSerializer() {
		return new NSECRecordSerializer();
	}

	private ZONECNAMERecordDeserializer zoneCNAMERecordDeserializer() {
		return new ZONECNAMERecordDeserializer();
	}

	private ZONECNAMERecordSerializer zoneCNAMERecordSerializer() {
		return new ZONECNAMERecordSerializer();
	}

	private URLRecordDeserializer urlRecordDeserializer() {
		return new URLRecordDeserializer();
	}

	private URLRecordSerializer urlRecordSerializer() {
		return new URLRecordSerializer();
	}
}
