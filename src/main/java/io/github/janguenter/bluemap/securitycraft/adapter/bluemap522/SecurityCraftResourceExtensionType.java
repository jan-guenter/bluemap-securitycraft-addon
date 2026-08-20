/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.adapter.bluemap522;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.util.Key;

/** Resource-pack extension factory registered before resource loading. */
final class SecurityCraftResourceExtensionType
        implements ResourcePack.Extension<SecurityCraftResourceExtension> {

    private static final Key KEY = Key.parse("bluemap_securitycraft:disguise_extension");
    private final SecurityCraftRuntime runtime;

    SecurityCraftResourceExtensionType(SecurityCraftRuntime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Key getKey() {
        return KEY;
    }

    @Override
    public SecurityCraftResourceExtension create(ResourcePack pack) {
        return new SecurityCraftResourceExtension(pack, runtime);
    }
}
