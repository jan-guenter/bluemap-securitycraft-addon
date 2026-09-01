/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.adapter.bluemap523;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePackExtension;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.world.BlockProperties;
import de.bluecolored.bluemap.core.world.BlockState;
import io.github.janguenter.bluemap.securitycraft.profile.ExactSecurityCraftArtifactDetector;
import io.github.janguenter.bluemap.securitycraft.profile.SecurityCraftProfile;

import java.nio.file.Path;

/** Exact activation and narrow synthetic routing for the persisted disguise route. */
final class SecurityCraftResourceExtension implements ResourcePackExtension {

    static final Key SYNTHETIC = Key.parse("bluemap_securitycraft:disguise");

    private final ResourcePack resourcePack;
    private final SecurityCraftRuntime runtime;

    SecurityCraftResourceExtension(
            ResourcePack resourcePack,
            SecurityCraftRuntime runtime
    ) {
        this.resourcePack = resourcePack;
        this.runtime = runtime;
    }

    @Override
    public void loadResources(Iterable<Path> roots) {
        if (Boolean.getBoolean("bluemap.securitycraft.disabled")) {
            runtime.inactive("operator-disabled");
        } else if (!ExactSecurityCraftArtifactDetector.matches(roots)) {
            runtime.inactive("exact-securitycraft-artifact-not-found");
        } else if (!validDispatch(resourcePack.getBlockStates().get(SYNTHETIC))) {
            runtime.inactive("synthetic-dispatch-invalid");
        } else {
            runtime.activate();
        }
    }

    @Override
    public void bake() {
        if (runtime.active()) {
            System.out.println("BlueMap SecurityCraft add-on active: routed "
                    + SecurityCraftProfile.HOST_IDS.size()
                    + " disguise-module hosts.");
        }
    }

    @Override
    public Key getBlockStateKey(Key key) {
        if (runtime.active() && SecurityCraftProfile.HOST_IDS.contains(key.getFormatted())) {
            return SYNTHETIC;
        }
        return key;
    }

    @Override
    public void getBlockProperties(BlockState state, BlockProperties.Builder builder) {
        if (runtime.active()
                && SecurityCraftProfile.HOST_IDS.contains(state.getId().getFormatted())) {
            builder.culling(false).occluding(false).cullingIdentical(false);
        }
    }

    private static boolean validDispatch(
            de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.BlockState state
    ) {
        return BlueMap523Adapter.isExpectedDispatch(state);
    }
}
