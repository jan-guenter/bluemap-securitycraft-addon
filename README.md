# BlueMap SecurityCraft Add-on

[![CI](https://github.com/jan-guenter/bluemap-securitycraft-addon/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/jan-guenter/bluemap-securitycraft-addon/actions/workflows/ci.yml)

A narrow Java 21 BlueMap 5.22 add-on that restores SecurityCraft's persisted
disguise-module material in static maps.

Version `0.1.0-alpha.1` is the owner-accepted prerelease. Its production JAR
is exactly 33,345 bytes with SHA-256
`4caccbbfaf9d413ae7d60e926069812e09da182adeb907ec2475083904d096e4`.

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
gradle --no-daemon \
  -PsecurityCraftJar=/tmp/securitycraft-1.10.2.1.jar \
  clean check build generatePomFileForAddonPublication verifyPinnedArtifact
```

The build uses the sibling `../bluemap-backport` checkout by default. The
production JAR is a plain BlueMap add-on for BlueMap's `packs` directory; it
is not a NeoForge mod.

`check` also verifies the final production and sources JARs byte-for-byte,
proves every non-manifest production-JAR entry equals the owner-accepted
staged JAR, and rejects any SecurityCraft artifact other than the exact pin.
The final manifest differs from staging only by replacing the prerelease
development version with `0.1.0-alpha.1`.

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
