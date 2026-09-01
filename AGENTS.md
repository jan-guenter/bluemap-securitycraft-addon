# Agent guide for BlueMap SecurityCraft Add-on

Read `/root/work/allthemons/AGENTS.md` before changing this standalone project.
This is a BlueMap add-on, not a NeoForge mod and not part of the root
orchestration repository.

The only supported profile is All the Mons `1.2.0`, Minecraft `1.21.1`,
NeoForge `21.1.248`, Java `21`, BlueMap backport
`5.22-feature.backport-5.23-stateless-java-web-server-46` at commit
`7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` with API commit
`285c9a60eff3ac2b0cab308ce1058d1565be0971`, and the exact 5,193,371-byte
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

Compile the four Adapter API helpers from exact gitlink
`e81f08bc4bfbf02d810ec8949a019130e2e61634`; never install, bundle, or nest
its standalone JAR. Keep local adapter code under `adapter.bluemap523`.

Initialize both submodules and run `gradle --no-daemon
-PbluemapSourcePath=/absolute/path/to/BlueMap-at-7e07f4e7
-PsecurityCraftJar=/tmp/securitycraft-1.10.2.1.jar
-PreleaseTag=v0.1.0-alpha.2 clean check build
generatePomFileForAddonPublication generateMetadataFileForAddonPublication
verifyPublicationArtifacts verifyReleaseCandidate`.
Owner visual acceptance is required before release; no release or deployment
is authorized by a successful build.
