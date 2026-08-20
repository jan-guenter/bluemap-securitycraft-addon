# Agent guide for BlueMap SecurityCraft Add-on

Read `/root/work/allthemons/AGENTS.md` before changing this standalone project.
This is a BlueMap add-on, not a NeoForge mod and not part of the root
orchestration repository.

The only supported profile is All the Mons `1.2.0`, Minecraft `1.21.1`,
NeoForge `21.1.248`, Java `21`, BlueMap backport
`5.22-agent.backport-5.22-mc1.21.1-2`, and the exact 5,193,371-byte
SecurityCraft `1.10.2.1` JAR whose SHA-256 is
`75ac9e73c60caf58df7069f167dbacc00a640e1418207dff654f56a5fdb5f229`.

Keep the route closed to the 31 exact disguise-module hosts. Decode only the
root `disguiseEnabled` and `Modules` data required to recover the saved block
state. Missing, malformed, oversized, AIR, recursive SecurityCraft, or
unsupported targets must render the original SecurityCraft block atomically.
Do not render contents, status, animation, targets, or unrelated
SecurityCraft block-entity renderers.

The implementation is independently authored MIT code. Do not bundle or copy
SecurityCraft, Minecraft, or BlueMap classes, models, textures, source,
worlds, or private fixtures. Keep `gallery/**` untouched unless the owner
explicitly assigns it.

Run `gradle --no-daemon -PsecurityCraftJar=/tmp/securitycraft-1.10.2.1.jar
clean check build generatePomFileForAddonPublication verifyPinnedArtifact`.
Owner visual acceptance is required before release; no release or deployment
is authorized by a successful build.
