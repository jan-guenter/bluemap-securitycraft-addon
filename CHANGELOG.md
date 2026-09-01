# Changelog

## 0.1.0-alpha.2 - 2026-09-01

- Migrate the adapter boundary to exact BlueMap feature-backport commit
  `7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` and API commit
  `285c9a60eff3ac2b0cab308ce1058d1565be0971`.
- Compile the four Adapter API `0.1.0-alpha.2` helpers from exact gitlink
  `e81f08bc4bfbf02d810ec8949a019130e2e61634`; remove the displaced local
  compatibility, registry, extension-type, and dispatch helpers.
- Rename the local adapter package from `bluemap522` to `bluemap523` while
  preserving the accepted 31-host renderer, profile, gallery, and fallback
  behavior.

## 0.1.0-alpha.1 - 2026-08-20

- Add the exact SecurityCraft 1.10.2.1 disguise-module rendering profile.
- Route only the 31 audited disguise-capable hosts and fail closed to their
  stock SecurityCraft appearance for malformed or unsupported saved states.
- Freeze the five-anchor owner-accepted gallery and its reproducible archive.
