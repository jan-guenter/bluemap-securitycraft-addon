/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.adapter.bluemap523;

import de.bluecolored.bluemap.core.world.BlockState;
import io.github.janguenter.bluemap.securitycraft.profile.SecurityCraftProfile;

import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/** Strict bounded decoder for the stable enabled disguise module. */
final class DisguiseSnapshotDecoder {

    private static final String DISGUISE_MODULE = "securitycraft:disguise_module";
    private static final int MAX_PROPERTIES = 32;
    private static final int MAX_TEXT = 128;
    private static final Pattern TOKEN = Pattern.compile("[a-z0-9_./:-]+");
    private static final Set<String> STANDING_TYPES = Set.of("none", "standing", "wall");

    Optional<BlockState> decode(SecurityCraftBlockEntityData data) {
        if (data == null || !data.disguiseEnabled()) {
            return Optional.empty();
        }
        List<SecurityCraftBlockEntityData.ModuleData> modules = data.modules();
        String blockEntityId = data.getId() == null ? null : data.getId().getFormatted();
        int capacity = SecurityCraftProfile.moduleCapacity(blockEntityId);
        if (modules == null || modules.isEmpty()
                || capacity == 0 || modules.size() > capacity) {
            return Optional.empty();
        }

        BlockState found = null;
        Set<Byte> occupiedSlots = new HashSet<>();
        for (SecurityCraftBlockEntityData.ModuleData module : modules) {
            if (module == null || module.moduleSlot() == null
                    || module.moduleSlot() < 0 || module.moduleSlot() >= capacity
                    || !occupiedSlots.add(module.moduleSlot())) {
                return Optional.empty();
            }
            if (!DISGUISE_MODULE.equals(module.id())) {
                continue;
            }
            int count = module.count() == null ? 1 : module.count();
            if (found != null || count != 1) {
                return Optional.empty();
            }
            SecurityCraftBlockEntityData.Components components = module.components();
            SecurityCraftBlockEntityData.SavedBlockState saved =
                    components == null ? null : components.savedBlockState();
            if (saved == null || !STANDING_TYPES.contains(saved.standingOrWallType())
                    || !valid(saved.state())) {
                return Optional.empty();
            }
            found = saved.state();
        }
        return Optional.ofNullable(found);
    }

    private static boolean valid(BlockState state) {
        if (state == null || state.isAir()) {
            return false;
        }
        String id = state.getId().getFormatted();
        if (id.length() > MAX_TEXT || id.startsWith("securitycraft:")
                || !TOKEN.matcher(id).matches()
                || state.getProperties().size() > MAX_PROPERTIES) {
            return false;
        }
        return state.getProperties().entrySet().stream().allMatch(entry ->
                entry.getKey() != null && entry.getValue() != null
                        && entry.getKey().length() <= MAX_TEXT
                        && entry.getValue().length() <= MAX_TEXT
                        && TOKEN.matcher(entry.getKey()).matches()
                        && TOKEN.matcher(entry.getValue()).matches());
    }
}
