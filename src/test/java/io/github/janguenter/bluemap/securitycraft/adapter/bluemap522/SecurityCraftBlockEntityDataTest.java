/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.adapter.bluemap522;

import de.bluecolored.bluemap.core.world.BlockState;
import de.bluecolored.bluemap.core.world.mca.MCAUtil;
import de.bluecolored.bluenbt.BlueNBT;
import de.bluecolored.bluenbt.NBTWriter;
import de.bluecolored.bluenbt.TagType;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityCraftBlockEntityDataTest {

    private final DisguiseSnapshotDecoder decoder = new DisguiseSnapshotDecoder();

    @Test
    void readsExactModernModuleAndSavedState() throws Exception {
        SecurityCraftBlockEntityData data = read(
                "securitycraft:keypad", true, "minecraft:oak_log", "axis", "x", 1
        );

        Optional<BlockState> state = decoder.decode(data);
        assertTrue(state.isPresent());
        assertEquals("securitycraft:keypad", data.getId().getFormatted());
        assertEquals("minecraft:oak_log", state.orElseThrow().getId().getFormatted());
        assertEquals("x", state.orElseThrow().getProperties().get("axis"));
    }

    @Test
    void disabledOrRecursiveDisguiseFallsBack() throws Exception {
        assertTrue(decoder.decode(read(
                "securitycraft:keypad", false, "minecraft:bricks", null, null, 1
        )).isEmpty());
        assertTrue(decoder.decode(read(
                "securitycraft:keypad", true, "securitycraft:keypad", null, null, 1
        )).isEmpty());
    }

    @Test
    void duplicateDisguiseModulesAndWrongCountFallBack() throws Exception {
        assertTrue(decoder.decode(read(
                "securitycraft:keypad", true, "minecraft:bricks", null, null, 2
        )).isEmpty());
        assertTrue(decoder.decode(read(
                "securitycraft:keypad", true, "minecraft:bricks", null, null, 1, 2
        )).isEmpty());
    }

    @Test
    void omittedDefaultCountIsAccepted() throws Exception {
        SecurityCraftBlockEntityData data = read(
                "securitycraft:keypad", true, "minecraft:bricks", null, null, null, 1
        );
        assertEquals("minecraft:bricks",
                decoder.decode(data).orElseThrow().getId().getFormatted());
    }

    @Test
    void slotOutsideTheExactHostInventoryFallsBack() throws Exception {
        SecurityCraftBlockEntityData data = readWithSlot(
                "securitycraft:observer", true, "minecraft:bricks", 1
        );
        assertTrue(decoder.decode(data).isEmpty());
    }

    @Test
    void laterModuleInTheSameSlotCannotLeaveAStaleDisguise() throws Exception {
        assertTrue(decoder.decode(readOverwrittenSlot()).isEmpty());
    }

    private static SecurityCraftBlockEntityData read(
            String blockEntityId,
            boolean enabled,
            String targetId,
            String property,
            String value,
            Integer count
    ) throws IOException {
        return read(blockEntityId, enabled, targetId, property, value, count, 1);
    }

    private static SecurityCraftBlockEntityData read(
            String blockEntityId,
            boolean enabled,
            String targetId,
            String property,
            String value,
            Integer count,
            int moduleCopies
    ) throws IOException {
        return readWithSlot(
                blockEntityId, enabled, targetId, property, value, count, moduleCopies, 0
        );
    }

    private static SecurityCraftBlockEntityData readWithSlot(
            String blockEntityId,
            boolean enabled,
            String targetId,
            int slot
    ) throws IOException {
        return readWithSlot(
                blockEntityId, enabled, targetId, null, null, 1, 1, slot
        );
    }

    private static SecurityCraftBlockEntityData readWithSlot(
            String blockEntityId,
            boolean enabled,
            String targetId,
            String property,
            String value,
            Integer count,
            int moduleCopies,
            int firstSlot
    ) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (NBTWriter writer = new NBTWriter(bytes)) {
            writer.name("").beginCompound();
            writer.name("id").value(blockEntityId);
            writer.name("x").value(170);
            writer.name("y").value(100);
            writer.name("z").value(164);
            writer.name("disguiseEnabled").value((byte) (enabled ? 1 : 0));
            writer.name("Modules").beginList(moduleCopies, TagType.COMPOUND);
            for (int index = 0; index < moduleCopies; index++) {
                writer.beginCompound();
                writer.name("ModuleSlot").value((byte) (firstSlot + index));
                writer.name("id").value("securitycraft:disguise_module");
                if (count != null) {
                    writer.name("count").value(count);
                }
                writer.name("components").beginCompound();
                writer.name("securitycraft:saved_block_state").beginCompound();
                writer.name("state").beginCompound();
                writer.name("Name").value(targetId);
                if (property != null) {
                    writer.name("Properties").beginCompound();
                    writer.name(property).value(value);
                    writer.endCompound();
                }
                writer.endCompound();
                writer.name("standing_or_wall_type").value("none");
                writer.endCompound();
                writer.endCompound();
                writer.endCompound();
            }
            writer.endList();
            writer.endCompound();
        }
        return MCAUtil.addCommonNbtSettings(new BlueNBT()).read(
                new ByteArrayInputStream(bytes.toByteArray()),
                SecurityCraftBlockEntityData.class
        );
    }

    private static SecurityCraftBlockEntityData readOverwrittenSlot() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (NBTWriter writer = new NBTWriter(bytes)) {
            writer.name("").beginCompound();
            writer.name("id").value("securitycraft:keypad");
            writer.name("disguiseEnabled").value((byte) 1);
            writer.name("Modules").beginList(2, TagType.COMPOUND);
            writer.beginCompound();
            writer.name("ModuleSlot").value((byte) 0);
            writer.name("id").value("securitycraft:disguise_module");
            writer.name("components").beginCompound();
            writer.name("securitycraft:saved_block_state").beginCompound();
            writer.name("state").beginCompound();
            writer.name("Name").value("minecraft:bricks");
            writer.endCompound();
            writer.name("standing_or_wall_type").value("none");
            writer.endCompound();
            writer.endCompound();
            writer.endCompound();
            writer.beginCompound();
            writer.name("ModuleSlot").value((byte) 0);
            writer.name("id").value("securitycraft:allowlist_module");
            writer.endCompound();
            writer.endList();
            writer.endCompound();
        }
        return MCAUtil.addCommonNbtSettings(new BlueNBT()).read(
                new ByteArrayInputStream(bytes.toByteArray()),
                SecurityCraftBlockEntityData.class
        );
    }
}
