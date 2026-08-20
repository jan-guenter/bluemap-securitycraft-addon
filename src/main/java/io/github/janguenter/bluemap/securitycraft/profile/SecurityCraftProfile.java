/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.profile;

import java.util.Map;
import java.util.Set;

/** Closed host and block-entity roster from SecurityCraft 1.10.2.1. */
public final class SecurityCraftProfile {

    public static final String ROUTE = "securitycraft-disguises";

    public static final Map<String, String> HOST_TO_BLOCK_ENTITY = Map.ofEntries(
            entry("block_change_detector", "block_change_detector"),
            entry("cage_trap", "cage_trap"),
            entry("floor_trap", "floor_trap"),
            entry("inventory_scanner", "inventory_scanner"),
            entry("keycard_reader", "keycard_reader"),
            entry("keypad", "keypad"),
            entry("keypad_barrel", "keypad_barrel"),
            entry("keypad_blast_furnace", "keypad_blast_furnace"),
            entry("keypad_chest", "keypad_chest"),
            entry("keypad_door", "keypad_door"),
            entry("keypad_furnace", "keypad_furnace"),
            entry("keypad_smoker", "keypad_smoker"),
            entry("keypad_trapdoor", "keypad_trapdoor"),
            entry("laser_block", "laser_block"),
            entry("projector", "projector"),
            entry("protecto", "protecto"),
            entry("reinforced_dispenser", "reinforced_dispenser"),
            entry("reinforced_dropper", "reinforced_dropper"),
            entry("reinforced_hopper", "reinforced_hopper"),
            entry("reinforced_observer", "observer"),
            entry("retinal_scanner", "retinal_scanner"),
            entry("rift_stabilizer", "rift_stabilizer"),
            entry("scanner_door", "scanner_door"),
            entry("scanner_trapdoor", "scanner_trapdoor"),
            entry("secure_trading_station", "secure_trading_station"),
            entry("security_camera", "security_camera"),
            entry("sentry_disguise", "disguisable"),
            entry("sonic_security_system", "sonic_security_system"),
            entry("trophy_system", "trophy_system"),
            entry("username_logger", "username_logger"),
            entry("secure_redstone_interface", "secure_redstone_interface")
    );

    public static final Set<String> HOST_IDS = Set.copyOf(HOST_TO_BLOCK_ENTITY.keySet());
    public static final Set<String> BLOCK_ENTITY_IDS =
            Set.copyOf(HOST_TO_BLOCK_ENTITY.values());
    public static final Map<String, Integer> BLOCK_ENTITY_MODULE_CAPACITIES = Map.ofEntries(
            capacity("block_change_detector", 4),
            capacity("cage_trap", 2),
            capacity("floor_trap", 3),
            capacity("inventory_scanner", 5),
            capacity("keycard_reader", 4),
            capacity("keypad", 5),
            capacity("keypad_barrel", 5),
            capacity("keypad_blast_furnace", 5),
            capacity("keypad_chest", 6),
            capacity("keypad_door", 5),
            capacity("keypad_furnace", 5),
            capacity("keypad_smoker", 5),
            capacity("keypad_trapdoor", 5),
            capacity("laser_block", 5),
            capacity("projector", 2),
            capacity("protecto", 3),
            capacity("reinforced_dispenser", 2),
            capacity("reinforced_dropper", 2),
            capacity("reinforced_hopper", 2),
            capacity("observer", 1),
            capacity("retinal_scanner", 2),
            capacity("rift_stabilizer", 5),
            capacity("scanner_door", 2),
            capacity("scanner_trapdoor", 2),
            capacity("secure_trading_station", 5),
            capacity("security_camera", 4),
            capacity("disguisable", 1),
            capacity("sonic_security_system", 3),
            capacity("trophy_system", 4),
            capacity("username_logger", 3),
            capacity("secure_redstone_interface", 1)
    );

    private SecurityCraftProfile() {
    }

    public static boolean matches(String hostId, String blockEntityId) {
        return blockEntityId != null
                && blockEntityId.equals(HOST_TO_BLOCK_ENTITY.get(hostId));
    }

    public static int moduleCapacity(String blockEntityId) {
        return BLOCK_ENTITY_MODULE_CAPACITIES.getOrDefault(blockEntityId, 0);
    }

    private static Map.Entry<String, String> entry(String host, String blockEntity) {
        return Map.entry("securitycraft:" + host, "securitycraft:" + blockEntity);
    }

    private static Map.Entry<String, Integer> capacity(String blockEntity, int capacity) {
        return Map.entry("securitycraft:" + blockEntity, capacity);
    }
}
