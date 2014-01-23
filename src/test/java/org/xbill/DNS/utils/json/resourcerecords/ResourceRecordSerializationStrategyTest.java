package org.xbill.DNS.utils.json.resourcerecords;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceRecordSerializationStrategyTest {

    @Test
    public void shouldAlwaysReturnFalseIfGlobalStrategyIsFullyExpandedRData() {
        ResourceRecordSerializationStrategy.setGlobalSerializationStrategy(
                ResourceRecordSerializationStrategy.SerializationStrategy.ALWAYS_FULLY_EXPANDED_RDATA);

        ResourceRecordSerializationStrategy.setRawRDataFormat(true);

        assertFalse(ResourceRecordSerializationStrategy.isRawRDataFormat());

        ResourceRecordSerializationStrategy.setRawRDataFormat(false);

        assertFalse(ResourceRecordSerializationStrategy.isRawRDataFormat());
    }

    @Test
    public void shouldAlwaysReturnTrueIfGlobalStrategyIsRawRData() {
        ResourceRecordSerializationStrategy.setGlobalSerializationStrategy(
                ResourceRecordSerializationStrategy.SerializationStrategy.ALWAYS_RAW_RDATA);

        ResourceRecordSerializationStrategy.setRawRDataFormat(true);

        assertTrue(ResourceRecordSerializationStrategy.isRawRDataFormat());

        ResourceRecordSerializationStrategy.setRawRDataFormat(false);

        assertTrue(ResourceRecordSerializationStrategy.isRawRDataFormat());
    }

    @Test
    public void shouldUseLocalThreadStrategyIfGlobalStrategyIsPerRequest() {
        ResourceRecordSerializationStrategy.setGlobalSerializationStrategy(
                ResourceRecordSerializationStrategy.SerializationStrategy.PER_REQUEST);

        ResourceRecordSerializationStrategy.setRawRDataFormat(true);

        assertTrue(ResourceRecordSerializationStrategy.isRawRDataFormat());

        ResourceRecordSerializationStrategy.setRawRDataFormat(false);

        assertFalse(ResourceRecordSerializationStrategy.isRawRDataFormat());
    }
}
