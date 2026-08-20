/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.adapter.bluemap522;

import de.bluecolored.bluemap.core.world.BlockState;
import de.bluecolored.bluemap.core.world.mca.blockentity.MCABlockEntity;
import de.bluecolored.bluenbt.NBTName;

import java.util.List;

/** Narrow BlueNBT projection of the persisted SecurityCraft disguise contract. */
public final class SecurityCraftBlockEntityData extends MCABlockEntity {

    @NBTName("disguiseEnabled")
    private Boolean disguiseEnabled;

    @NBTName("Modules")
    private List<ModuleData> modules;

    public SecurityCraftBlockEntityData() {
    }

    boolean disguiseEnabled() {
        return Boolean.TRUE.equals(disguiseEnabled);
    }

    List<ModuleData> modules() {
        return modules;
    }

    /** Persisted ItemStack entry augmented with SecurityCraft's module slot. */
    public static final class ModuleData {

        @NBTName("ModuleSlot")
        private Byte moduleSlot;
        private String id;
        private Integer count;
        private Components components;

        public ModuleData() {
        }

        Byte moduleSlot() {
            return moduleSlot;
        }

        String id() {
            return id;
        }

        Integer count() {
            return count;
        }

        Components components() {
            return components;
        }
    }

    /** ItemStack component compound. */
    public static final class Components {

        @NBTName("securitycraft:saved_block_state")
        private SavedBlockState savedBlockState;

        public Components() {
        }

        SavedBlockState savedBlockState() {
            return savedBlockState;
        }
    }

    /** Exact persistent payload of SecurityCraft's SavedBlockState codec. */
    public static final class SavedBlockState {

        private BlockState state;

        @NBTName("standing_or_wall_type")
        private String standingOrWallType;

        public SavedBlockState() {
        }

        BlockState state() {
            return state;
        }

        String standingOrWallType() {
            return standingOrWallType;
        }
    }
}
