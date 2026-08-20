/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.securitycraft.adapter.bluemap522;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/** Shared activation state and bounded diagnostics for the single route. */
final class SecurityCraftRuntime {

    static final SecurityCraftRuntime INSTANCE = new SecurityCraftRuntime();
    private static final int MAX_DIAGNOSTICS = 8;

    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicInteger diagnostics = new AtomicInteger();

    private SecurityCraftRuntime() {
    }

    boolean active() {
        return active.get();
    }

    void activate() {
        active.set(true);
    }

    void inactive(String reason) {
        active.set(false);
        report("inactive-" + reason);
    }

    void report(String reason) {
        if (diagnostics.incrementAndGet() <= MAX_DIAGNOSTICS) {
            System.err.println("BlueMap SecurityCraft add-on: " + reason + ".");
        }
    }
}
