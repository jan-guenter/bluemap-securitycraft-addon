/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.adapter.bluemap522;

import de.bluecolored.bluemap.core.resources.adapter.ResourcesGson;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.world.BlockState;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityCraftPropertySchemaTest {

    @Test
    void rejectsUnknownOrInvalidPropertiesWithoutRejectingExactSelectors() {
        var resource = ResourcesGson.INSTANCE.fromJson(
                """
                {"variants":{
                  "axis=x":{"model":"minecraft:block/oak_log_horizontal"},
                  "axis=y":{"model":"minecraft:block/oak_log"},
                  "axis=z":{"model":"minecraft:block/oak_log_horizontal"}
                }}
                """,
                de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.BlockState.class
        );

        assertTrue(SecurityCraftRenderer.propertiesMatchResource(
                resource, state(Map.of("axis", "x"))));
        assertFalse(SecurityCraftRenderer.propertiesMatchResource(
                resource, state(Map.of("axis", "x", "bogus", "x"))));
        assertFalse(SecurityCraftRenderer.propertiesMatchResource(
                resource, state(Map.of("axis", "invalid"))));
    }

    @Test
    void permitsOnlyTheKnownModelInertFalseWaterloggedValue() {
        var resource = ResourcesGson.INSTANCE.fromJson(
                """
                {"variants":{
                  "facing=east,half=bottom,shape=straight":
                    {"model":"minecraft:block/oak_stairs"}
                }}
                """,
                de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.BlockState.class
        );
        assertTrue(SecurityCraftRenderer.propertiesMatchResource(
                resource,
                state("minecraft:oak_stairs", Map.of(
                        "facing", "east", "half", "bottom", "shape", "straight",
                        "waterlogged", "false"
                ))
        ));
        assertFalse(SecurityCraftRenderer.propertiesMatchResource(
                resource,
                state("minecraft:oak_stairs", Map.of(
                        "facing", "east", "half", "bottom", "shape", "straight",
                        "waterlogged", "false", "bogus", "x"
                ))
        ));

        var propertyFree = ResourcesGson.INSTANCE.fromJson(
                "{\"variants\":{\"\":{\"model\":\"minecraft:block/bricks\"}}}",
                de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.BlockState.class
        );
        assertFalse(SecurityCraftRenderer.propertiesMatchResource(
                propertyFree,
                state("minecraft:bricks", Map.of("waterlogged", "false"))
        ));
    }

    private static BlockState state(Map<String, String> properties) {
        return state("minecraft:test", properties);
    }

    private static BlockState state(String id, Map<String, String> properties) {
        return new BlockState(Key.parse(id), properties);
    }
}
