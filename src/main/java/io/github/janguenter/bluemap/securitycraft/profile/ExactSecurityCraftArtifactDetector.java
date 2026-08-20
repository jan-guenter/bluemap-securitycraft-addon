/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.profile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Set;

/** Exact-byte activation gate for the All the Mons 1.2.0 SecurityCraft artifact. */
public final class ExactSecurityCraftArtifactDetector {

    public static final long SIZE = 5_193_371L;
    public static final String SHA256 =
            "75ac9e73c60caf58df7069f167dbacc00a640e1418207dff654f56a5fdb5f229";
    private static final int MAX_ROOTS = 4096;

    private ExactSecurityCraftArtifactDetector() {
    }

    public static boolean matches(Iterable<Path> roots) {
        int count = 0;
        Set<Path> inspected = new HashSet<>();
        for (Path root : roots) {
            if (++count > MAX_ROOTS || Thread.currentThread().isInterrupted()) {
                return false;
            }
            try {
                if (root == null || !Files.isRegularFile(root) || Files.size(root) != SIZE) {
                    continue;
                }
                Path real = root.toRealPath();
                if (inspected.add(real) && SHA256.equals(digest(real))) {
                    return true;
                }
            } catch (IOException exception) {
                return false;
            }
        }
        return false;
    }

    private static String digest(Path path) throws IOException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 unavailable", exception);
        }
        byte[] buffer = new byte[64 * 1024];
        try (InputStream input = Files.newInputStream(path)) {
            int read;
            while ((read = input.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
        return HexFormat.of().formatHex(digest.digest());
    }
}
