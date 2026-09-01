# BlueMap SecurityCraft Add-on

[![CI](https://github.com/jan-guenter/bluemap-securitycraft-addon/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/jan-guenter/bluemap-securitycraft-addon/actions/workflows/ci.yml)

A narrow Java 21 add-on for the exact BlueMap 5.23 feature backport that
restores SecurityCraft's persisted disguise-module material in static maps.

Version `0.1.0-alpha.2` is the unpublished BlueMap 5.23 migration candidate.
Its production JAR is exactly 36,586 bytes with SHA-256
`02d3e95321fd9dfec5886c34bd6fed0f4df67ad01dc6cc6afa8a1cae9ea46071`.
It preserves the owner-accepted alpha.1 renderer, profile, gallery, and
fallback behavior.

The exact profile activates only for SecurityCraft `1.10.2.1` with JAR size
5,193,371 bytes and SHA-256
`75ac9e73c60caf58df7069f167dbacc00a640e1418207dff654f56a5fdb5f229`.
It routes exactly the 30 disguisable blocks declared by the pinned client
handler plus `securitycraft:secure_redstone_interface`.

For a valid enabled disguise module, the add-on renders the saved ordinary
resource blockstate in place of the SecurityCraft host. Any unsupported or
failed decode/model emission resets the partial result and renders the
original SecurityCraft state. Targets in the `securitycraft` namespace are
rejected to prevent recursive dispatch. Contents, status, animation and all
other block-entity rendering remain stock or intentionally absent.

## Build

```bash
git submodule update --init --recursive -- \
  tooling/bluemap-addon-toolkit modules/bluemap-addon-adapter-api
gradle --no-daemon \
  -PbluemapSourcePath=/absolute/path/to/BlueMap-at-7e07f4e7 \
  -PsecurityCraftJar=/tmp/securitycraft-1.10.2.1.jar \
  -PreleaseTag=v0.1.0-alpha.2 \
  clean check build generatePomFileForAddonPublication \
  generateMetadataFileForAddonPublication verifyPublicationArtifacts \
  verifyReleaseCandidate
```

The exact BlueMap checkout is commit
`7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` with API commit
`285c9a60eff3ac2b0cab308ce1058d1565be0971`. The Adapter API source module is
pinned at commit `e81f08bc4bfbf02d810ec8949a019130e2e61634`, source tree
`2f974c9bb2ba13888d69682f86f30f58922d30eb`; exactly four helpers are compiled
as source and no module JAR is installed, bundled, or nested. The production
JAR is a plain BlueMap add-on for BlueMap's `packs` directory, not a NeoForge
mod.

`check` also verifies the final production and sources JARs byte-for-byte,
requires the exact shared source/class roster, rejects displaced local helper
types, and rejects any SecurityCraft artifact other than the exact pin.

Tagged releases publish the production and sources JARs, POM, Gradle module
metadata, and checksums at Maven coordinate
`io.github.jan-guenter:bluemap-securitycraft-addon:<version>`. The annotated
tag must equal `v<addon_version>`.

## Gallery

The tracked five-anchor gallery covers a bare keypad plus brick, axis-aware
oak-log, directional oak-stair, and glass disguise controls. It performs 16
retained-state assertions immediately, after 20 ticks, and after 100 ticks.
Its reproducible datapack ZIP is exactly 4,130 bytes with SHA-256
`b14c2a650aaa0302c48d918f23d854e92dc520f2cc944498c30c5912a13f84d5`.
No third-party assets are bundled.

## Installation

Place the reviewed add-on JAR in `config/bluemap/packs` and restart the JVM.
SecurityCraft remains an operator-installed mod in the server's `mods`
directory. The add-on writes no world or player data.

## Compatibility and fallback

Only the exact All the Mons 1.2.0 profile is supported. An absent or different
SecurityCraft artifact leaves the add-on inactive. Removing the add-on and
restarting restores BlueMap's normal resource renderer without changing world
data.

The exact disposable staging runtime, retained-state checks, rendered gallery,
and browser view were owner accepted on 2026-08-20. Compatibility outside the
exact pinned profile is not asserted.
