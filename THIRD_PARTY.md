# Third-party components

| Component | Version / identity | License | Distribution posture |
| --- | --- | --- | --- |
| BlueMap | 5.22 / workspace Java 21 backport | MIT | Compile-only API; not bundled |
| SecurityCraft | 1.10.2.1, exact SHA-256 in `provenance/upstreams.json` | MIT declared by exact artifact | Operator-installed evidence/resource provider; not bundled |
| Minecraft / NeoForge | 1.21.1 / 21.1.248 | Third-party terms | Not bundled |

The add-on interprets operator-installed blockstate resources and persisted
NBT. Its implementation is independently authored; upstream source was used
only as reference evidence for registry and persisted-data field identities.
