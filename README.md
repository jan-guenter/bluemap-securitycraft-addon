# BlueMap SecurityCraft Add-on

A narrow Java 21 BlueMap 5.22 add-on that restores SecurityCraft's persisted
disguise-module material in static maps.

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

## Compatibility and fallback

Only the exact All the Mons 1.2.0 profile is supported. An absent or different
SecurityCraft artifact leaves the add-on inactive. Removing the add-on and
restarting restores BlueMap's normal resource renderer without changing world
data.

No runtime lifecycle, visual acceptance, release, or production deployment is
claimed until that exact test occurs.
