/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.profile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityCraftProfileTest {

    @Test
    void exactClientRosterAndBlockEntityExceptionsAreClosed() {
        assertEquals(31, SecurityCraftProfile.HOST_IDS.size());
        assertEquals(31, SecurityCraftProfile.BLOCK_ENTITY_IDS.size());
        assertEquals(31, SecurityCraftProfile.BLOCK_ENTITY_MODULE_CAPACITIES.size());
        assertEquals(SecurityCraftProfile.BLOCK_ENTITY_IDS,
                SecurityCraftProfile.BLOCK_ENTITY_MODULE_CAPACITIES.keySet());
        assertTrue(SecurityCraftProfile.HOST_IDS.contains(
                "securitycraft:secure_redstone_interface"));
        assertTrue(SecurityCraftProfile.matches(
                "securitycraft:reinforced_observer", "securitycraft:observer"));
        assertTrue(SecurityCraftProfile.matches(
                "securitycraft:sentry_disguise", "securitycraft:disguisable"));
        assertFalse(SecurityCraftProfile.matches(
                "securitycraft:reinforced_observer", "securitycraft:reinforced_observer"));
        assertFalse(SecurityCraftProfile.HOST_IDS.contains("minecraft:stone"));
        assertEquals(6, SecurityCraftProfile.moduleCapacity("securitycraft:keypad_chest"));
        assertEquals(1, SecurityCraftProfile.moduleCapacity("securitycraft:observer"));
    }
}
