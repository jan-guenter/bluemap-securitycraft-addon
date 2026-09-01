# Release procedure

Releases are promoted only from an owner-accepted, independently audited
commit on `main`.

1. Initialize exact submodules with `git submodule update --init --recursive --
   tooling/bluemap-addon-toolkit modules/bluemap-addon-adapter-api`.
2. Verify the exact BlueMap checkout and SecurityCraft input, then run
   `gradle --no-daemon
   -PbluemapSourcePath=/absolute/path/to/BlueMap-at-7e07f4e7
   -PsecurityCraftJar=/absolute/path/securitycraft-1.10.2.1.jar
   -PreleaseTag=v0.1.0-alpha.2 clean check build
   generatePomFileForAddonPublication generateMetadataFileForAddonPublication
   verifyPublicationArtifacts verifyReleaseCandidate`.
3. Inspect both JARs. Require exactly the four shared Adapter API source/class
   paths once; reject displaced local helpers, nested JARs, upstream classes or
   assets, gallery output, tests, research data, and unexpanded metadata.
4. Run the gallery and browser review against the exact candidate, obtain owner
   acceptance, then reproduce all four artifact identities before release.
5. Merge the required version pull request. `addon_version` must be final and
   must not end in `-SNAPSHOT`.
6. Create and push an annotated `v<addon_version>` tag at the reviewed `main`
   commit.
7. The release workflow reproduces every accepted byte, creates a draft
   prerelease, uploads and attests the assets, publishes the Maven package,
   verifies the draft assets, and only then makes the prerelease public.

Do not reuse or move a release tag. A failed prepublication run may be resumed
with the workflow's exact immutable tag input while its GitHub release remains
a draft.
