/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.profile;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExactSecurityCraftArtifactDetectorTest {

    @Test
    void acceptsOnlyTheExternallySuppliedExactJar() {
        String configured = System.getProperty("securityCraftJar");
        assertTrue(configured != null && !configured.isBlank(),
                "test JVM needs -PsecurityCraftJar=<exact JAR>");
        Path exact = Path.of(configured);
        assertTrue(ExactSecurityCraftArtifactDetector.matches(List.of(exact)));
        assertFalse(ExactSecurityCraftArtifactDetector.matches(List.of(
                Path.of("src/main/resources/bluemap.addon.json"))));
    }
}
